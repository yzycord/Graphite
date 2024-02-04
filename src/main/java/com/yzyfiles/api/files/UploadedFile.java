package com.yzyfiles.api.files;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UploadedFile {
    @Id
    public String id; // the mongodb id

    public String uploadId;
    public String fileName;

    // most likely not needed as we have id
    //public String path;
    // not yet
    //public String uploaderId;

    public UploadedFile() {}

    public UploadedFile(String uploadId, String fileName) {
        this.uploadId = uploadId;
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "UploadedFile{" + "uploadId=" + this.uploadId + ", fileName='" + this.fileName + "}";
    }

}
