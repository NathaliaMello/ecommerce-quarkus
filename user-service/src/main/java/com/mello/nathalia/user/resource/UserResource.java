package com.mello.nathalia.user.resource;

import com.mello.nathalia.user.domain.User;
import com.mello.nathalia.user.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

@Path( "/users")
@Produces("application/json")
@Consumes("application/json")
public class UserResource {

    private final UserRepository userRepository;

    @Inject
    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GET
    public List<User> getAll() {
        return userRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return userRepository.findByIdOptional(id)
                .map(user -> Response.ok(user).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    public Response create(User user) {
        try {
            userRepository.persist(user);
            return Response.status(Response.Status.CREATED)
                    .entity(user)
                    .build();
        } catch (ConstraintViolationException e) {
            if ("uq_users_email".equals(e.getConstraintName())) {
                return Response.status(Response.Status.CONFLICT)
                        .entity(new ErrorResponse("EMAIL_ALREADY_EXISTS", "Email já cadastrado"))
                        .build();
            }
            throw e;
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        return userRepository.findByIdOptional(id)
                .map(user -> {
                    userRepository.delete(user);
                    return Response.noContent().<Response>build();
                })
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

}
