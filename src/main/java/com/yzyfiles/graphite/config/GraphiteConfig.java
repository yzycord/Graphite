package com.yzyfiles.graphite.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class GraphiteConfig {

    @Value("${graphite.upload-path}")
    private String uploadPath;

}
