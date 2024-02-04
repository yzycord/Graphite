package com.yzyfiles.api.controllers;

import com.yzyfiles.api.files.UploadedFile;
import com.yzyfiles.api.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/uploads")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // api/v1/uploads
    @GetMapping()
    public List<UploadedFile> getUploadedFiles() {
        return fileService.getUploadedFiles();
    }

    @GetMapping("{uploadId}")
    @ResponseBody
    public UploadedFile getUploadedFile(@PathVariable String uploadId) {
        return fileService.getUploadedFile(uploadId);
    }

    // api/v1/uploads/{uploadId}
    // @PostMapping("upload/{uploadId}")
    // @ResponseBody
    // public UploadedFile postUploadedFile(@PathVariable String uploadId) {
    //     return json of file uploaded
    // }

    // api/v1/uploads/{uploadId}
    // @DeleteMapping("upload/{uploadId}")
    // @ResponseBody
    // public UploadedFile deleteUploadedFile(@PathVariable String uploadId) {
    //     return json of file uploaded
    // }
}
