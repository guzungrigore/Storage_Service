package com.faf.storage.dto.request;

import com.faf.storage.util.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.Set;

public record SignUpRequest(
        Long id,

        @NotBlank
        @Pattern(regexp = Constants.LOGIN_REGEX)
        @Size(min = 1, max = 50)
        String login,

        @NotBlank
        @Size(min = 4, max = 100)
        String password,

        @NotBlank
        @Size(max = 50)
        String firstName,

        @NotBlank
        @Size(max = 50)
        String lastName,

        @NotBlank
        @Email
        @Size(min = 5, max = 254)
        String email,

        @NotNull
        Long reservedSpace,

        @Size(max = 256)
        String imageUrl,

        boolean activated,

        @Size(min = 2, max = 10)
        String langKey,

        String createdBy,

        Instant createdDate,

        String lastModifiedBy,

        Instant lastModifiedDate,

        Set<String> authorities
) {
}
