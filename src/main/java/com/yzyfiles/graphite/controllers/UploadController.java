package com.yzyfiles.graphite.controllers;

import com.yzyfiles.graphite.data.FileData;
import com.yzyfiles.graphite.services.UploadedService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/uploads")
public class UploadController {

    private final UploadedService fileService;

    public UploadController(UploadedService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public List<FileData> getUploads() {
        return fileService.getUploads();
    }

    @GetMapping("{uploadId}")
    @ResponseBody
    public FileData getFileById(@PathVariable String uploadId) {
        return fileService.getUpload(uploadId);
    }

    @PostMapping
    @ResponseBody
    public FileData createFile(@RequestParam("file") MultipartFile multipartFile) {
        return fileService.createUploadedFile(multipartFile);
    }

//    @DeleteMapping("{uploadId}")
//    @ResponseBody
//    public UploadedFile deleteUploadedFile(@PathVariable String uploadId) {
//        return fileService.deleteUploadedFile(uploadId);
//    }
}
