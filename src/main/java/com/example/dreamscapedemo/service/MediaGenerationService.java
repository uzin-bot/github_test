package com.example.dreamscapedemo.service;

import com.example.dreamscapedemo.entity.Dream;
import com.example.dreamscapedemo.entity.DreamMedia;
import com.example.dreamscapedemo.hailuo.HailuoClient;
import com.example.dreamscapedemo.repository.DreamMediaRepository;
import com.example.dreamscapedemo.repository.DreamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MediaGenerationService {

    private final HailuoClient hailuoClient;
    private final DreamRepository dreamRepository;
    private final DreamMediaRepository dreamMediaRepository;

    public DreamMedia generateForDream(Long dreamId, String prompt) {
        Dream dream = dreamRepository.findById(dreamId)
                .orElseThrow(() -> new IllegalArgumentException("Dream not found: " + dreamId));

        var submitted = hailuoClient.submit(prompt, null);

        var media = DreamMedia.builder()
                .dream(dream)
                .provider("hailuo")
                .taskId(submitted.taskId())
                .status(submitted.status())
                .mediaType("VIDEO")
                .build();
        dreamMediaRepository.save(media);

        long deadline = System.currentTimeMillis() + 1000L * 60 * 5; // 5분 대기
        while (System.currentTimeMillis() < deadline) {
            var s = hailuoClient.getStatus(submitted.taskId());
            media.setStatus(s.status());
            if ("succeed".equalsIgnoreCase(s.status())) {
                media.setUrl(s.url());       // 최종 URL (url 필드)
                media.setMediaUrl(s.url());  // 네가 mediaUrl도 함께 쓰면 이 줄 유지
                return media;
            }
            if ("failed".equalsIgnoreCase(s.status())) {
                throw new IllegalStateException("Hailuo generation failed: " + submitted.taskId());
            }
            try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
        }
        throw new IllegalStateException("Hailuo generation timeout: " + submitted.taskId());
    }
}
