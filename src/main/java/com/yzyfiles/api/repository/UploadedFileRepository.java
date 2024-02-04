package com.yzyfiles.api.repository;

import com.yzyfiles.api.files.UploadedFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UploadedFileRepository extends MongoRepository<UploadedFile, String> {

    Optional<UploadedFile> findByUploadId(String uploadId);
}