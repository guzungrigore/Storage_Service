package com.faf.storage.service;

import com.faf.storage.domain.StorageFile;
import com.faf.storage.domain.User;
import com.faf.storage.dto.ResponseDto;
import com.faf.storage.repository.StorageFileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private final StorageFileRepository fileRepository;

    public FileService(StorageFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public ResponseDto uploadFile(MultipartFile request) {
        StorageFile newFile = new StorageFile();
        newFile.setName(request.getOriginalFilename());
        newFile.setMimeType(request.getContentType());
        newFile.setSize(request.getSize());


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();


        newFile.setCreatedBy(user.getLogin());
        newFile.setUser(user);

        fileRepository.save(newFile);

        return new ResponseDto("Test");
    }


}
