package com.yzyfiles.graphite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ShutdownController {

    private final ApplicationContext applicationContext;

    @Autowired
    public ShutdownController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostMapping("/shutdown")
    public void shutdown() {
        // Perform any necessary cleanup or saving operations here
        SpringApplication.exit(applicationContext);
    }
}
