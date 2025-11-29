package com.example.dreamscapedemo.service.impl;

import com.example.dreamscapedemo.dto.MediaResponseDTO;
import com.example.dreamscapedemo.entity.Dream;
import com.example.dreamscapedemo.entity.DreamMedia;
import com.example.dreamscapedemo.repository.DreamMediaRepository;
import com.example.dreamscapedemo.repository.DreamRepository;
import com.example.dreamscapedemo.service.MediaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final DreamRepository dreamRepository;
    private final DreamMediaRepository dreamMediaRepository;

    @Override
    @Transactional
    public MediaResponseDTO createDummyMedia(Long dreamId) {

        // 1. Dream 조회
        Dream dream = dreamRepository.findById(dreamId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 dreamId 입니다: " + dreamId));

        // 2. 1차 구현
        String dummyUrl = "https://example.com/dummy-video.mp4";
        String mediaType = "VIDEO";

        // 3. DreamMedia 엔티티 생성
        DreamMedia media = DreamMedia.builder()
                .dream(dream)
                .mediaType(mediaType)
                .mediaUrl(dummyUrl)
                .build();

        // 4. 저장
        DreamMedia saved = dreamMediaRepository.save(media);

        // 5. 엔티티 -> DTO 변환
        return MediaResponseDTO.builder()
                .id(saved.getId())
                .mediaType(saved.getMediaType())
                .mediaurl(saved.getMediaUrl())
                .build();
    }
}
