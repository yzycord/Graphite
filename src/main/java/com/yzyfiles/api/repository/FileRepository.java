package com.yzyfiles.api.repository;

import java.io.File;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<File, String> {

    public File findByUploadId(String id);

}