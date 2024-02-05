package com.yzyfiles.graphite.controllers;

import com.yzyfiles.graphite.data.UploadData;
import com.yzyfiles.graphite.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/uploads")
public class UploadController {

    private final UploadService uploadService;

    @Autowired
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @GetMapping
    public List<UploadData> getUploads() {
        return uploadService.getUploads();
    }

    @GetMapping("{uploadId}")
    @ResponseBody
    public UploadData getUploadById(@PathVariable String uploadId) {
        return uploadService.getUpload(uploadId);
    }

    @PostMapping
    @ResponseBody
    public UploadData createUpload(@RequestParam("file") MultipartFile multipartFile) {
        // consider just returning id
        return uploadService.createUpload(multipartFile);
    }
}
