package com.yzyfiles.graphite.controllers;

import com.yzyfiles.graphite.services.UploadedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/download")
public class DownloadController {

    private final UploadedService fileService;

    public DownloadController(UploadedService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("{uploadId}")
    public ResponseEntity<byte[]> downloadById(@PathVariable String uploadId) {
        return fileService.getUploadedFileBytesByFileHash(fileService.getUpload(uploadId));
    }
}
