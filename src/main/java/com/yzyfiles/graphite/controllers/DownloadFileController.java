package com.yzyfiles.graphite.controllers;

import com.yzyfiles.graphite.services.UploadedFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/download")
public class DownloadFileController {

    private final UploadedFileService fileService;

    public DownloadFileController(UploadedFileService fileService) {

        this.fileService = fileService;
    }


    @GetMapping("{uploadId}")
    public ResponseEntity<byte[]> downloadById(@PathVariable String uploadId) {
        return fileService.getUploadedFileBytesByFileHash(fileService.getUploadedFile(uploadId));
    }
}
