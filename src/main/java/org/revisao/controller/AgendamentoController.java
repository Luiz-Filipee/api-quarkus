package org.revisao.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
}
