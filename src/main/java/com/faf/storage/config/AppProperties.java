package com.faf.storage.config;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "storage")
public record AppProperties(Minio minio) {
    public record Minio(@NotEmpty String url, @NotEmpty String rootUser, @NotEmpty String rootPassword) {
    }
}
