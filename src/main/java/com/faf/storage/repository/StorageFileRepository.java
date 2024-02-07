package com.faf.storage.repository;

import com.faf.storage.domain.StorageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageFileRepository extends JpaRepository<StorageFile, Long> {
}
