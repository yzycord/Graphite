package com.yzyfiles.graphite.repositories;

import com.yzyfiles.graphite.data.UploadData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UploadRepository extends MongoRepository<UploadData, String> {

    Optional<UploadData> findByUploadId(String uploadId);
}