package com.example.entity.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


// 알람
@Entity
@Getter
@Setter
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드 포함한 생성자 자동 생성
@Builder // 빌더 패턴으로 객체 생성
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId; // PK (자동 증가)

    @Column(nullable = false)
    private String message; // 알림에 표시될 메시지 내용

    @Column
    private LocalDateTime scheduledAt; // 알림 예정 시각

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키(FK)
    private User user; // 알림을 받을 사용자

}
