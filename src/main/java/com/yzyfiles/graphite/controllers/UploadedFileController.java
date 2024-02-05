package com.yzyfiles.graphite.controllers;

import com.yzyfiles.graphite.files.UploadedFile;
import com.yzyfiles.graphite.services.UploadedFileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/uploads")
public class UploadedFileController {

    private final UploadedFileService fileService;

    public UploadedFileController(UploadedFileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping()
    public List<UploadedFile> getUploadedFiles() {
        return fileService.getUploadedFiles();
    }

    @GetMapping("{uploadId}")
    @ResponseBody
    public UploadedFile getUploadedFile(@PathVariable String uploadId) {
        return fileService.getUploadedFile(uploadId);
    }

    @PostMapping()
    @ResponseBody
    public UploadedFile postUploadedFile(@RequestParam("file") MultipartFile multipartFile) {
        return fileService.createUploadedFile(multipartFile);
    }

//    @DeleteMapping("{uploadId}")
//    @ResponseBody
//    public UploadedFile deleteUploadedFile(@PathVariable String uploadId) {
//        return fileService.deleteUploadedFile(uploadId);
//    }
}
