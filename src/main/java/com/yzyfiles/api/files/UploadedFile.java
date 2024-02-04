package com.yzyfiles.api.files;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class UploadedFile {

    @Id
    public String id; // the mongodb id

    public String uploadId;
    public String fileName;

    public UploadedFile() {}

    public UploadedFile(String id, String uploadId, String fileName) {
        this.id = id;
        this.uploadId = uploadId;
        this.fileName = fileName;
    }

    public UploadedFile(String uploadId, String fileName) {
        this.uploadId = uploadId;
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "UploadedFile{" + "Id=" + this.id + ", " + "uploadId=" + this.uploadId + ", fileName='" + this.fileName + "}";
    }

}
