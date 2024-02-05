package com.yzyfiles.graphite.services;

import com.yzyfiles.graphite.config.GraphiteConfig;
import com.yzyfiles.graphite.data.UploadData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DownloadService {

    private final GraphiteConfig graphiteConfig;
    private final UploadService uploadService;

    @Autowired
    public DownloadService(GraphiteConfig graphiteConfig, UploadService uploadService) {
        this.graphiteConfig = graphiteConfig;
        this.uploadService = uploadService;
    }

    public ResponseEntity<byte[]> downloadFile(String uploadId) {
        // this just feels messy but idk

        UploadData uploadData = uploadService.getUpload(uploadId);
        String fileHash = uploadData.getFileHash();
        Path filePath = Paths.get(graphiteConfig.getUploadPath(), fileHash, fileHash + ".file");

        try(InputStream inputStream = Files.newInputStream(filePath)) {
            byte[] fileBytes = inputStream.readAllBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(uploadData.getContentType()));
            ContentDisposition contentDisposition = ContentDisposition
                .builder("attachment")
                .filename(uploadData.getFileName())
                .build();
            headers.setContentDisposition(contentDisposition);

            return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File bytes were not found", e);
        }
    }
    // was thinking maybe separate download stuff from other service into here
}
