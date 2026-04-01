package org.revisao.controller;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.revisao.model.HorarioEntity;
import org.revisao.model.UserEntity;

import java.util.List;

@Path("horarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class HorarioController {

    @GET
    @Path("/user/{userId}")
    public List<HorarioEntity> findHorariosByUser(@PathParam("userId") Long userId) {
        return HorarioEntity.list("user.id", userId);
    }

    @POST
    @Transactional
    public Response create(HorarioEntity horarioEntity) {
        UserEntity user = UserEntity.find("id", horarioEntity.user.id).firstResult();

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuario nao encontrado!")
                    .build();
        }

        horarioEntity.persist();
        return Response.status(Response.Status.CREATED).entity(horarioEntity).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, HorarioEntity horarioUpdate) {
        HorarioEntity entity = HorarioEntity.findById(id);

        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Horario não encontrado")
                    .build();
        }

        if (horarioUpdate.diaSemana != null) {
            entity.diaSemana = horarioUpdate.diaSemana;
        }
        if (horarioUpdate.horarioInicio != null) {
            entity.horarioInicio = horarioUpdate.horarioInicio;
        }
        if (horarioUpdate.horarioFim != null) {
            entity.horarioFim = horarioUpdate.horarioFim;
        }

        return Response.ok(entity).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("îd") Long id) {
        HorarioEntity entity = HorarioEntity.findById(id);

        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        boolean isDeletado = HorarioEntity.deleteById(entity);

        return Response.ok(isDeletado).build();
    }
}
