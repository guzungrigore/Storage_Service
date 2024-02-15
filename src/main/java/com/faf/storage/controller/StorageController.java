package com.faf.storage.controller;

import com.faf.storage.dto.ResponseDto;
import com.faf.storage.dto.StorageDto;
import com.faf.storage.service.StorageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/storage")
public class StorageController {

    private final StorageService storageService;
    
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public ResponseEntity<List<StorageDto>> getAllStorages() {
        return ResponseEntity.status(OK).body(storageService.getAllStorages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StorageDto> getStorageById(@PathVariable Long id) {
        return ResponseEntity.status(OK).body(storageService.getStorageById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseDto> saveStorage(@RequestBody @Valid StorageDto storageDto) {
        return ResponseEntity.status(CREATED).body(storageService.saveStorage(storageDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateStorage(@PathVariable Long id,
                                                  @RequestBody @Valid StorageDto storageDto) {
        return ResponseEntity.status(OK).body(storageService.updateStorage(id, storageDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteStorage(@PathVariable Long id) {
        return ResponseEntity.status(OK).body(storageService.deleteStorage(id));
    }
}
