package com.faf.storage.controller;

import com.faf.storage.domain.StorageFile;
import com.faf.storage.dto.ResponseDto;
import com.faf.storage.service.FileService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;
    
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseDto> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return ResponseEntity.status(CREATED).body(fileService.uploadFile(file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StorageFile> getMetadata(@PathVariable @NotNull Long id) {
        return ResponseEntity.status(OK).body(fileService.getMetadata(id));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable @NotNull Long id) {
        String objectName = fileService.getMetadata(id).getName();

        return ResponseEntity.status(OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + objectName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileService.download(id));
    }

    @DeleteMapping("/{id}" )
    public ResponseEntity<ResponseDto> deleteFile(@PathVariable @NotNull Long id) {
        return ResponseEntity.status(OK).body(fileService.deleteFile(id));
    }

}
