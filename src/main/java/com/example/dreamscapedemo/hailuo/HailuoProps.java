package com.example.dreamscapedemo.hailuo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hailuo")
public record HailuoProps(
        String baseUrl,
        String generationPath,
        String taskStatusPath,
        String apiKey,
        String webhookUrl
) {}