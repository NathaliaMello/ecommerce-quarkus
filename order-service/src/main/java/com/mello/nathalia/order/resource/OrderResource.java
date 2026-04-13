package com.mello.nathalia.order.resource;

import com.mello.nathalia.order.domain.Order;
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
    private final OrderRepository orderRepository;

    @Inject
    public OrderResource(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @GET
    @RolesAllowed({"admin", "user"})
    public List<Order> getAll() {
        return orderRepository.listAll();
    }

    @GET
    @Path("/user/{userId}")
    @RolesAllowed({"admin", "user"})
    public List<Order> getByUserId(@PathParam("userId") Long userId) {
        return orderRepository.findByUserId(userId);
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
}
