package com.faf.storage.controller;

import com.faf.storage.dto.ResponseDto;
import com.faf.storage.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.CREATED;

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















//    @GetMapping
//    public ResponseEntity<List<StorageDto>> getAllStorages() {
//        return ResponseEntity.status(OK).body(fileService.getAllStorages());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<StorageDto> getStorageById(@PathVariable Long id) {
//        return ResponseEntity.status(OK).body(fileService.getStorageById(id));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ResponseDto> updateStorage(@PathVariable Long id,
//                                                  @RequestBody @Valid StorageDto storageDto) {
//        return ResponseEntity.status(OK).body(fileService.updateStorage(id, storageDto));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ResponseDto> deleteStorage(@PathVariable Long id) {
//        return ResponseEntity.status(OK).body(fileService.deleteStorage(id));
//    }
}
