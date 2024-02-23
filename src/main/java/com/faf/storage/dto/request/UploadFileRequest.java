package com.faf.storage.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UploadFileRequest(
        @NotBlank String name,
        @NotBlank String mimeType) {
}
