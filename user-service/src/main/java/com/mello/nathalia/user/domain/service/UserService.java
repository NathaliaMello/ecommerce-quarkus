package com.mello.nathalia.user.domain.service;

import com.mello.nathalia.user.common.response.ErrorResponse;
import com.mello.nathalia.user.domain.model.User;
import com.mello.nathalia.user.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User create(User user) {
        try {
            userRepository.persist(user);
            return user;
        } catch (ConstraintViolationException e) {
            if ("uq_users_email".equals(e.getConstraintName())) {
                throw new WebApplicationException(
                        Response.status(Response.Status.CONFLICT)
                                .entity(new ErrorResponse(
                                        "EMAIL_ALREADY_EXISTS",
                                        "Email já cadastrado"))
                                .build()
                );
            }
            throw e;
        }
    }

    @Transactional
    public boolean delete(Long id) {
        return userRepository.findByIdOptional(id)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }

    public List<User> findAll() {
        return userRepository.listAll();
    }

    public User findById(Long id) {
        return userRepository.findByIdOptional(id)
                .orElseThrow(() -> new WebApplicationException(
                        Response.status(Response.Status.NOT_FOUND)
                                .entity(new ErrorResponse("USER_NOT_FOUND", "Usuário não encontrado"))
                                .build()
                ));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new WebApplicationException(
                        Response.status(Response.Status.NOT_FOUND)
                                .entity(new ErrorResponse("USER_NOT_FOUND", "Usuário não encontrado"))
                                .build()
                ));
    }

}
