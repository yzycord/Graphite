package com.yzyfiles.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/files")
public class FilesController {
    @GetMapping("uploads")
    public String uploads() {
        return "uploads";
    }
}
