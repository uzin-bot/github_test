package com.example.dreamscapedemo.hailuo;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(HailuoProps.class)
public class HailuoConfig {
    @Bean
    public WebClient hailuoWebClient(HailuoProps props, WebClient.Builder builder) {
        return builder
                .baseUrl(props.baseUrl())
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("x-api-key", props.apiKey())
                .build();
    }
}
