package com.example.dreamscapedemo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "dream")
public class Dream {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dream_id")
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(name = "raw_text", columnDefinition = "TEXT")
    private String rawText;

    @Column(name = "ai_summary", columnDefinition = "TEXT")
    private String aiSummary;

    @Column(name = "mood", length = 50)
    private String mood;

    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "dream_tags", joinColumns = @JoinColumn(name = "dream_id"))
    @Column(name = "tag")
    private List<String> tags;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "dream_media_urls", joinColumns = @JoinColumn(name = "dream_id"))
    @Column(name = "media_url")
    private List<String> mediaUrls;

    public void updateDream(String title, String rawText, String mood) {
        if (title != null && !title.isEmpty()) {
            this.title = title;
        }
        if (rawText != null && !rawText.isEmpty()) {
            this.rawText = rawText;
        }
        if (mood != null && !mood.isEmpty()) {
            this.mood = mood;
        }
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.recordedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}