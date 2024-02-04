package com.yzyfiles.api.repository;

import com.yzyfiles.api.files.UploadedFile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<UploadedFile, String> {

    public UploadedFile findByUploadId(String uploadId);

}