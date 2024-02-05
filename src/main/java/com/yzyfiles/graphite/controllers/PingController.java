package com.yzyfiles.graphite.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Doesnt need versioning

@CrossOrigin
@RestController
@RequestMapping("api/ping")
public class PingController {
    @GetMapping
    public String ping() {
        return "pong";
    }
}
