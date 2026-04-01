package org.revisao.controller;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.revisao.dto.CreateUserRequest;
import org.revisao.dto.UserAuthRequest;
import org.revisao.model.UserEntity;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;

@Path("auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

    @POST
    @Path("/login")
    @Transactional
    public Response login(UserAuthRequest userAuthRequest) {
        UserEntity user = UserEntity.find("email", userAuthRequest.email()).firstResult();

        if (user == null || !BcryptUtil.matches(userAuthRequest.password(), user.password)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Email ou senha inválidos")
                    .build();
        }

        // Geração do Token JWT
        String token = Jwt.issuer("https://meu-barber-api.com")
                .upn(user.email) // Nome do usuário (Subject)
                .groups(new HashSet<>(Arrays.asList("USER"))) // Roles/Perfil
                .claim("userId", user.id) // Dados extras (payload)
                .expiresIn(Duration.ofHours(8))
                .sign(); // Assina o token

        return Response.ok(token).build();
    }

    @POST
    @Path("/register")
    @Transactional
    public Response register(CreateUserRequest newUser) {
        if (UserEntity.find("email", newUser.email()).firstResult() != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Erro: Este e-mail já está cadastrado.")
                    .build();
        }

        UserEntity user = new UserEntity(newUser);
        user.password = BcryptUtil.bcryptHash(newUser.password());
        user.persist();

        return Response.status(Response.Status.CREATED)
                .entity("Usuario criado com sucesso")
                .build();
    }
}
