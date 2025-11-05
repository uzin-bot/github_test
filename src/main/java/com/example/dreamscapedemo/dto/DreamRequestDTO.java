package com.example.dreamscapedemo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DreamRequestDTO {
    @NotBlank private String title; // 제목
    @NotBlank private String rawText; // 원문(사용자 입력)
    private List<String> tags;
    private String emotion; // 감정 1개 선택
    }

