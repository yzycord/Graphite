package com.yzyfiles.api.controllers;

import com.yzyfiles.api.files.UploadedFile;
import com.yzyfiles.api.repository.FileRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/file")
public class FileController {

    private final FileRepository fileRepository;
    FileController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @GetMapping("uploads")
    public List<UploadedFile> uploadedFiles() {
        return fileRepository.findAll();
    }

    @GetMapping("upload/{uploadId}")
    @ResponseBody
    public UploadedFile getUploadedFile(@PathVariable String uploadId) {
        return fileRepository.findByUploadId(uploadId);
    }

    // @PostMapping("upload/{uploadId}")
    // @ResponseBody
    // return json of file uploaded
    // public UploadedFile postUploadedFile(@PathVariable String uploadId) {

    // @DeleteMapping("upload/{uploadId}")
    // @ResponseBody
    // public UploadedFile deleteUploadedFile(@PathVariable String uploadId) {
    // return json of file deleted
    
}
