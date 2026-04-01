package org.revisao.dto;

import io.smallrye.common.constraint.NotNull;

public record UserAuthRequest(
        @NotNull
        String email,

        @NotNull
        String password
) {
}
