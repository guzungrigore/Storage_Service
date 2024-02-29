package com.faf.storage.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class MainBucketCreationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final MinioClient minioClient;

    private final AppProperties appProperties;

    public MainBucketCreationListener(MinioClient minioClient, AppProperties appProperties) {
        this.minioClient = minioClient;
        this.appProperties = appProperties;
    }

    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        System.out.println("TEST283495725982376523985634");
        try {
            String mainBucket = appProperties.minio().mainBucket();
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(mainBucket).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(mainBucket).build());
            }
        } catch (MinioException | IllegalArgumentException | IOException | NoSuchAlgorithmException |
                 InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
