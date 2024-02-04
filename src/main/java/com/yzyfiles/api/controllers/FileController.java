package com.yzyfiles.api.controllers;

import com.yzyfiles.api.files.UploadedFile;
import com.yzyfiles.api.repository.FileRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/file")
public class FileController {

    private final FileRepository fileRepository;
    FileController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @GetMapping("uploads")
    List<UploadedFile> all() {
        return fileRepository.findAll();
    }
}
