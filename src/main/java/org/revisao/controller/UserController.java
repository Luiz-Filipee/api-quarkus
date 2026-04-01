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

    @GET
    public List<UserEntity> findAllUsers() {
        return UserEntity.listAll();
    }

    @GET
    @Path("email/{email}")
    public Response findByEmail(@PathParam("email") String email) {
        return UserEntity.findByEmail(email)
                .map(user -> Response.ok(user).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    public Response createUser(UserEntity user) {
        user.persist();
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, UserEntity userUpdate) {
        UserEntity entity = UserEntity.findById(id);

        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuário não encontrado")
                    .build();
        }

        if (userUpdate.email != null) {
            entity.email = userUpdate.email;
        }
        if (userUpdate.username != null) {
            entity.username = userUpdate.username;
        }

        return Response.ok(entity).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUser(@PathParam("îd") Long id) {
        UserEntity entity = UserEntity.findById(id);

        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        boolean isDeletado = UserEntity.deleteById(entity);

        return Response.ok(isDeletado).build();
    }
}

