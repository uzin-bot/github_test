package com.example.entity.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드 포함한 생성자 자동 생성
@Builder // 빌더 패턴으로 객체 생성
public class DreamMedia {
    @Id
    private String dreamId;

    @Column
    private String mediaUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dreamId") // FK 컬럼 이름
    private Dream dream;

}
