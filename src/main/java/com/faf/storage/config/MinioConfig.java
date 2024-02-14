package com.faf.storage.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    private final AppProperties properties;

    public MinioConfig(AppProperties properties) {
        this.properties = properties;
    }

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(properties.minio().url())
                .credentials(properties.minio().rootUser(), properties.minio().rootPassword())
                .build();
    }
}
