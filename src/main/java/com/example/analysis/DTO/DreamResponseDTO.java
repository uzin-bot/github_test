package com.example.analysis.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DreamResponseDTO {
    private Long dreamId; // 꿈 고유 ID
    private String title; // 제목
    private String aiSummary; // AI 요약 결과
    private String aiInterpretation; // 꿈 해몽 결과 (새로 추가됨)
    private String mood; // 감정 또는 분위기
    private String rawText; // 원문(사용자가 작성한 텍스트)
    private LocalDateTime recordedAt; // 기록 시각
    private LocalDateTime createdAt; // 생성 시각
    private String tag; // 태그 (수정)
    private String originalMediaUrl; //원본 URL (수정)
    private String editedMediaUrl; // 수정 URL (수정)
}
