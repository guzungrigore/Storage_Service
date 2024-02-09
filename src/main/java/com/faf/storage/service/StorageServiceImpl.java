package com.faf.storage.service;

import com.faf.storage.dto.ResponseDto;
import com.faf.storage.dto.StorageDto;
import com.faf.storage.service.api.StorageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {
    @Override
    public List<StorageDto> getAllStorages() {
        return null;
    }

    @Override
    public StorageDto getStorageById(Long id) {
        return null;
    }

    @Override
    public ResponseDto saveStorage(StorageDto storageDto) {
        return null;
    }

    @Override
    public ResponseDto updateStorage(Long id, StorageDto storageDto) {
        return null;
    }

    @Override
    public ResponseDto deleteStorage(Long id) {
        return null;
    }
}
