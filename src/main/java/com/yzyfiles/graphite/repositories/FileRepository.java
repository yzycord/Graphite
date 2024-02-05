package com.yzyfiles.graphite.repositories;

import com.yzyfiles.graphite.data.FileData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends MongoRepository<FileData, String> {

    Optional<FileData> findByUploadId(String uploadId);
}