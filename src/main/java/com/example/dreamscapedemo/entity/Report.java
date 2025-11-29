package com.example.dreamscapedemo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드 포함한 생성자 자동 생성
@Builder // 빌더 패턴으로 객체 생성
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId; // PK (자동 증가)

    @Column
    private String period; // 보고서 기간 (예: "2025-03", "2025-11 주간")

    @Column
    private String summary; // 보고서 요약 내용

    @Column
    private int dreamCount; // 해당 기간의 꿈 기록 수 -> 굳이 필요없을듯

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // FK 설정
    private User user;
}
