package com.example.dreamscapedemo;

import com.example.dreamscapedemo.service.MediaGenerationService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DreamScapeDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DreamScapeDemoApplication.class, args);
    }

    @Bean
    ApplicationRunner hailuoTest(MediaGenerationService mediaService) {
        return args -> {
            //Long dreamId = 1L; // H2 DB에 미리 있는 Dream ID
           // String prompt = "a peaceful dream of floating islands above clouds, cinematic 5s";
            //var media = mediaService.generateForDream(dreamId, prompt);
            //System.out.println(" Hailuo video generated!");
            //System.out.println("Status: " + media.getStatus());
            //System.out.println("URL: " + media.getUrl());
        };
    }
}
