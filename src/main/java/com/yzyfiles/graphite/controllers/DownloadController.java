package com.yzyfiles.graphite.controllers;

import com.yzyfiles.graphite.services.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/download")
public class DownloadController {

    private final DownloadService downloadService;

    @Autowired
    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping("{uploadId}")
    public ResponseEntity<byte[]> downloadByFileData(@PathVariable String uploadId) {
        return downloadService.downloadFile(uploadId);
    }
}
