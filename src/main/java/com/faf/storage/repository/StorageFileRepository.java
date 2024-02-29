package com.faf.storage.repository;

import com.faf.storage.domain.StorageFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorageFileRepository extends JpaRepository<StorageFile, Long> {

    @NotNull
    Optional<StorageFile> findById(@NotNull Long id);
}
