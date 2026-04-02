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

    @Inject
    UserService userService;

    @Inject
    JwtService jwtService;

    @POST
    @Path("/login")
    @Transactional
    public Response login(UserAuthRequest userAuthRequest) {
        UserEntity user = userService.findByEmail(userAuthRequest.email());

        if (user == null || !BcryptUtil.matches(userAuthRequest.password(), user.password)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Email ou senha inválidos")
                    .build();
        }

        String token = jwtService.generateToken(user);

        return Response.ok(new LoginResponse(token)).build();
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
