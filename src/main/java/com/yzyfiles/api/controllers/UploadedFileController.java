package com.yzyfiles.api.controllers;

import com.yzyfiles.api.files.UploadedFile;
import com.yzyfiles.api.services.UploadedFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/uploads")
public class UploadedFileController {

    private final UploadedFileService fileService;

    @Autowired
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

    @PostMapping("upload")
    @ResponseBody
    public UploadedFile postUploadedFile(@RequestBody UploadedFile uploadedFile) {
        return fileService.createUploadedFile(uploadedFile);
    }

    // @DeleteMapping("upload/{uploadId}")
    // @ResponseBody
    // public UploadedFile deleteUploadedFile(@PathVariable String uploadId) {
    //     return json of file uploaded
    // }
}
