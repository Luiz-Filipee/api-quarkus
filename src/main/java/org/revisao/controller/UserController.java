package org.revisao.controller;

import io.quarkus.security.Authenticated;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.revisao.model.UserEntity;

import java.util.List;

@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class UserController {

    @Inject
    UserService userService;

    @GET
    public List<UserEntity> findAllUsers() {
        return userService.findAll();
    }

    @GET
    @Path("email/{email}")
    public Response findByEmail(@PathParam("email") String email) {
        return userService.findByEmail(email)
                .map(user -> Response.ok(user).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response createUser(UserEntity user) {
        user = userService.create(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @PUT
    @Path("{id}")
    public Response updateUser(@PathParam("id") Long id, UserEntity userUpdate) {
        UserEntity entity = userService.update(id, userUpdate);

        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Usuário não encontrado")
                .build();
        }

        return Response.ok(entity).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        boolean isDeleted = userService.delete(id);

        if (!isDeleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(isDeleted).build();
    }
}

