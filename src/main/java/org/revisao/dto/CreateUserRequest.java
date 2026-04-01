package org.revisao.dto;

import io.smallrye.common.constraint.NotNull;
import org.revisao.enums.Role;

public record CreateUserRequest(
        @NotNull
        String username,

        @NotNull
        String email,

        @NotNull
        String password,

        @NotNull
        Role role
) {
}
