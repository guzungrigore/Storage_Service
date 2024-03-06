package com.faf.storage.service;

import com.faf.storage.config.AppProperties;
import com.faf.storage.domain.StorageFile;
import com.faf.storage.domain.User;
import com.faf.storage.domain.UserReservation;
import com.faf.storage.dto.ResponseDto;
import com.faf.storage.exception.FileException;
import com.faf.storage.exception.NotEnoughSpaceException;
import com.faf.storage.repository.StorageFileRepository;
import com.faf.storage.repository.UserRepository;
import com.faf.storage.repository.UserReservationRepository;
import com.faf.storage.service.strategy.FileStrategy;
import com.faf.storage.util.SecurityUtils;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FileService {
    private final AppProperties appProperties;

    private final UserReservationRepository userReservationRepository;

    private final StorageFileRepository fileRepository;

    private final UserRepository userRepository;

    private final MinioClient minioClient;

    private final List<FileStrategy> strategies;

    public FileService(UserReservationRepository userReservationRepository,
                       StorageFileRepository fileRepository,
                       UserRepository userRepository,
                       MinioClient minioClient,
                       AppProperties appProperties,
                       List<FileStrategy> strategies) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
        this.minioClient = minioClient;
        this.userReservationRepository = userReservationRepository;
        this.appProperties = appProperties;
        this.strategies = strategies;
    }

    public ResponseDto uploadFile(MultipartFile request) {
        if (request == null || request.isEmpty()) {
            throw new FileException("File is missing");
        }

        User user = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin)
                .orElseThrow(EntityNotFoundException::new);

        checkUserReservation(request.getSize(), user);

        String userLogin = user.getLogin();

        String uniqueFileName = UUID.randomUUID().toString();
        String objectName = userLogin + "/" + uniqueFileName;

        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(appProperties.minio().mainBucket())
                    .object(objectName)
                    .contentType(request.getContentType())
                    .stream(request.getInputStream(), request.getSize(), -1)
                    .build());

        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        StorageFile newFile = new StorageFile();
        newFile.setName(request.getOriginalFilename());
        newFile.setMimeType(request.getContentType());
        newFile.setSize(request.getSize());
        newFile.setCreatedBy(userLogin);
        newFile.setUser(user);

        newFile.setPath(objectName);

        fileRepository.save(newFile);

        return new ResponseDto("File uploaded successfully");
    }

    private void checkUserReservation(long size, User user) {
        UserReservation userReservation = userReservationRepository.findByUser(user)
                .orElseThrow(EntityNotFoundException::new);

        Long usedSize = userReservation.getUsedSize();
        if (usedSize + size > userReservation.getTotalSize()) {
            throw new NotEnoughSpaceException();
        }

        userReservation.setUsedSize(usedSize + size);
    }

    public ResponseDto deleteFile(Long id) {
        StorageFile file = fileRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        UserReservation userReservation = userReservationRepository.findByUser(file.getUser())
                .orElseThrow(EntityNotFoundException::new);

        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                            .bucket(appProperties.minio().mainBucket())
                            .object(file.getPath())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file from storage: " + e.getMessage(), e);
        }

        userReservation.setUsedSize(userReservation.getUsedSize() - file.getSize());

        fileRepository.delete(file);

        return new ResponseDto("File deleted successfully");
    }

    public StorageFile getMetadata(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public byte[] download(Long id) {
        StorageFile file = fileRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        try {
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(appProperties.minio().mainBucket())
                            .object(file.getPath())
                            .build());
            return stream.readAllBytes();

        } catch (Exception e) {
            throw new RuntimeException("Failed to download file", e);
        }
    }

    public byte[] downloadReport(String fileType) {

        for (FileStrategy strategy : strategies) {
            if (strategy.supports(fileType)) {
                return strategy.generateFile();
            }
        }
        throw new IllegalArgumentException("Unsupported format: " + fileType);
    }




//    @Component
//    private class PdfFileStrategy implements FileStrategy {
//        @Override
//        public byte[] generateFile() {
//            return null;
//        }
//
//        @Override
//        public boolean supports(String format) {
//            return "pdf".equalsIgnoreCase(format);
//        }
//    }
//
//    @Component
//    private class CsvFileStrategy implements FileStrategy {
//        // Implement the methods
//        @Override
//        public byte[] generateFile() {
//            // CSV generation logic
//            return null;
//        }
//
//        @Override
//        public boolean supports(String format) {
//            return "csv".equalsIgnoreCase(format);
//        }
//    }


}
