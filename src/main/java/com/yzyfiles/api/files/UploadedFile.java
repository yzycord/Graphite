package com.yzyfiles.api.files;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Document
public class UploadedFile {

    @Id
    private String id; // the mongodb id
    private String uploadId;
    private String fileName;
    private LocalDateTime createdAt;

    public UploadedFile() {}

    public UploadedFile(String uploadId, String fileName) {
        this.uploadId = uploadId;
        this.fileName = fileName;
        this.createdAt = LocalDateTime.now();
    }
}
