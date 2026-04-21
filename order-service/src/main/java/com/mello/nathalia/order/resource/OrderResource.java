package com.mello.nathalia.order.resource;

import com.mello.nathalia.order.common.response.ErrorResponse;
import com.mello.nathalia.order.domain.model.Order;
import com.mello.nathalia.order.domain.service.OrderService;
import com.mello.nathalia.order.repository.OrderRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private final OrderService orderService;

    @Inject
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GET
    @RolesAllowed({"admin", "user"})
    public List<Order> getAll() {
        return orderService.getAll();
    }

    @GET
    @Path("/user/{userId}")
    @RolesAllowed({"admin", "user"})
    public List<Order> getByUserId(@PathParam("userId") Long userId) {
        return orderService.getByUserId(userId);
    }

    @POST
    @RolesAllowed({"admin", "user"})
    public Response create(Order order,
                           @HeaderParam("Idempotency-Key") String idempotencyKey) {
        try {
            Order created = orderService.create(order, idempotencyKey);
            return Response.status(Response.Status.CREATED)
                    .entity(created)
                    .build();
        } catch (WebApplicationException e) {
            int status = e.getResponse().getStatus();
            if (status == 404) {
                return Response.status(422)
                        .entity(new ErrorResponse("USER_NOT_FOUND", "Usuário não encontrado"))
                        .build();
            }
            return e.getResponse();
        }
    }

    @GET
    @Path("/user/{userId}/summary")
    @RolesAllowed({"admin", "user"})
    public Response getSummaryByUserId(@PathParam("userId") Long userId) {
        return Response.ok(orderService.getSummaryByUserId(userId)).build();
    }
}
