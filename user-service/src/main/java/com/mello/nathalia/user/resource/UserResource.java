package com.mello.nathalia.user.resource;

import com.mello.nathalia.user.domain.model.User;
import com.mello.nathalia.user.domain.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@Path( "/users")
@Produces("application/json")
@Consumes("application/json")
public class UserResource {

    @Inject
    JsonWebToken jwt;

    private final UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @RolesAllowed({"admin", "user"})
    public List<User> getAll() {
        return userService.findAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"admin", "user"})
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(userService.findById(id)).build();
    }

    @POST
    @RolesAllowed("admin")
    public Response create(User user) {
        User created = userService.create(user);
        return Response.status(Response.Status.CREATED)
                .entity(created)
                .build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = userService.delete(id);
        return deleted
                ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/me")
    @RolesAllowed({"admin", "user"})
    public Response getMe() {
        String email = jwt.getClaim("email");
        User user = userService.findByEmail(email);
        return Response.ok(user).build();
    }

}
