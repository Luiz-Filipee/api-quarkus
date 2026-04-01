package org.revisao.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.revisao.model.AgendamentoEntity;
import org.revisao.model.HorarioEntity;
import org.revisao.model.ServicoEntity;
import org.revisao.model.UserEntity;

@Path("agendamento")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AgendamentoController {

    @POST
    @Transactional
    public Response create(AgendamentoEntity agendamento) {
        UserEntity user = UserEntity.find("id", agendamento.usuario.id).firstResult();
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuario nao encontrado!")
                    .build();
        }

        HorarioEntity horario = HorarioEntity.find("id", agendamento.horario.id).firstResult();
        if (horario == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Horario nao encontrado!")
                    .build();
        }

        ServicoEntity servico = ServicoEntity.find("id", agendamento.servico).firstResult();
        if (servico == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Servico nao encontrado!")
                    .build();
        }

        agendamento.persist();
        return Response.status(Response.Status.CREATED).entity(agendamento).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, AgendamentoEntity agendamentoUpdate) {
        AgendamentoEntity entity = AgendamentoEntity.findById(id);
        
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Agendamento não encontrado")
                .build();
        }

        if (agendamentoUpdate.usuario != null) {
            UserEntity user = UserEntity.find("id", agendamentoUpdate.usuario.id).firstResult();
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuario nao encontrado!")
                    .build();
            }
            entity.usuario = user;
        }

        if (agendamentoUpdate.horario != null) {
            HorarioEntity horario = HorarioEntity.find("id", agendamentoUpdate.horario.id).firstResult();
            if (horario == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("Horario nao encontrado!")
                    .build();
            }
            entity.horario = horario;
        }

        if (agendamentoUpdate.servico != null) {
            ServicoEntity servico = ServicoEntity.find("id", agendamentoUpdate.servico).firstResult();
            if (servico == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("Servico nao encontrado!")
                    .build();
            }
            entity.servico = servico;
        }

        return Response.ok(entity).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("îd") Long id) {
        AgendamentoEntity entity = AgendamentoEntity.findById(id);

        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        boolean isDeletado = AgendamentoEntity.deleteById(entity);

        return Response.ok(isDeletado).build();
    }
}
