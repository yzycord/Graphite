package com.yzyfiles.graphite.data;

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
public class UploadData {
    @Id
    private String id;
    private String uploadId;
    private String fileHash;
    private String fileName;
    private String contentType;
    private LocalDateTime createdAt;
}
