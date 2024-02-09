package com.faf.storage.service.api;

import com.faf.storage.dto.ResponseDto;
import com.faf.storage.dto.StorageDto;

import java.util.List;

public interface StorageService {
    List<StorageDto> getAllStorages();

    StorageDto getStorageById(Long id);

    ResponseDto saveStorage(StorageDto storageDto);

    ResponseDto updateStorage(Long id, StorageDto storageDto);

    ResponseDto deleteStorage(Long id);
}
