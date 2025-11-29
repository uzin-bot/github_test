package com.example.dreamscapedemo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DreamResponseDTO {

    private Long dreamId; // 꿈 고유 ID

    private String title; // 제목

    private String aiSummary; // AI 해몽/요약 결과

    private String mood; // 감정 또는 분위기

    private String rawText; // 원문(사용자가 작성한 텍스트)

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime recordedAt; // 기록 시각

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt; // 생성 시각

    private List<String> tags; // 태그 목록

    private List<String> mediaUrls; // mediaUrl 리스트
}