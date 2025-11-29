package com.example.dreamscapedemo.service.impl;

import com.example.dreamscapedemo.entity.Dream;
import com.example.dreamscapedemo.entity.User;
import com.example.dreamscapedemo.repository.DreamRepository;
import com.example.dreamscapedemo.repository.UserRepository;
import com.example.dreamscapedemo.service.DreamService;
import com.example.dreamscapedemo.dto.DreamRequestDTO;
import com.example.dreamscapedemo.dto.DreamResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DreamServiceImpl implements DreamService {

    // 의존성 주입 (생성자 주입)
    private final DreamRepository dreamRepository;
    private final UserRepository userRepository;

    //새로운 꿈 기록 생성
    @Override
    public DreamResponseDTO createDream(Long userId, DreamRequestDTO dto) {
        // 유저 존재 확인 (없으면 예외 발생)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "존재하지 않는 유저입니다. userId: " + userId));

        // Dream 엔티티 생성 (Builder 패턴)
        Dream dream = Dream.builder()
                .user(user)                              // 유저 정보 연결
                .title(dto.getTitle())                   // 제목
                .rawText(dto.getRawText())               // 꿈 내용
                .mood(dto.getMood())                     // 감정
                .tags(dto.getTags())                     // 태그들
                .recordedAt(LocalDateTime.now())         // 기록 시간 = 현재시간
                .createdAt(LocalDateTime.now())          // 생성 시간 = 현재시간
                .build();

        // DB에 저장
        Dream savedDream = dreamRepository.save(dream);
        // 저장 시 @PrePersist가 자동으로 실행되어 시간 설정됨

        // ResponseDTO로 변환하여 반환
        return convertToResponseDTO(savedDream);
    }

    //특정 꿈 1개 조회

    @Override
    @Transactional(readOnly = true)
    // readOnly = true: 조회만 하므로 성능 최적화
    public DreamResponseDTO getDream(Long dreamId) {
        // dreamId로 꿈 조회
        Dream dream = dreamRepository.findById(dreamId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "존재하지 않는 꿈입니다. dreamId: " + dreamId));

        //ResponseDTO로 변환하여 반환
        return convertToResponseDTO(dream);
    }

    // 특정 유저의 모든 꿈 조회
    @Override
    @Transactional(readOnly = true)
    public List<DreamResponseDTO> getDreamsByUser(Long userId) {
        // 1. 유저 존재 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "존재하지 않는 유저입니다. userId: " + userId));

        // 2. Repository에서 유저의 모든 꿈을 최신순으로 조회
        List<Dream> dreams = dreamRepository.findByUserIdOrderByCreatedAtDesc(userId);

        // 3. Stream 사용: Dream → ResponseDTO 변환 → List로 수집
        return dreams.stream()
                .map(this::convertToResponseDTO)  // 각 Dream을 ResponseDTO로 변환
                .collect(Collectors.toList());     // List로 변환
    }

    //꿈 정보 수정
    @Override
    public DreamResponseDTO updateDream(Long dreamId, DreamRequestDTO dto) {
        // dreamId로 꿈 조회
        Dream dream = dreamRepository.findById(dreamId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "존재하지 않는 꿈입니다. dreamId: " + dreamId));

        // 받은 정보로 꿈 업데이트
        dream.setTitle(dto.getTitle());
        dream.setRawText(dto.getRawText());
        dream.setMood(dto.getMood());
        dream.setTags(dto.getTags());
        // @PreUpdate가 자동으로 실행되어 updatedAt이 갱신됨

        // 저장 (변경사항 DB에 반영)
        Dream updatedDream = dreamRepository.save(dream);

        // ResponseDTO로 변환하여 반환
        return convertToResponseDTO(updatedDream);
    }

    // D: 꿈 삭제
    @Override
    public void deleteDream(Long dreamId) {
        // dreamId로 꿈 조회
        Dream dream = dreamRepository.findById(dreamId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "존재하지 않는 꿈입니다. dreamId: " + dreamId));

        // Repository를 통해 꿈 삭제
        dreamRepository.delete(dream);
    }

    // 유틸리티 메서드: Dream 엔티티 → ResponseDTO 변환

    private DreamResponseDTO convertToResponseDTO(Dream dream) {
        return DreamResponseDTO.builder()
                .dreamId(dream.getId())                    // 꿈 ID
                .title(dream.getTitle())                   // 제목
                .aiSummary(dream.getAiSummary())           // AI 요약
                .mood(dream.getMood())                     // 감정
                .rawText(dream.getRawText())               // 원본 텍스트
                .recordedAt(dream.getRecordedAt())         // 기록 시간
                .createdAt(dream.getCreatedAt())           // 생성 시간
                .tags(dream.getTags())                     // 태그
                .mediaUrls(dream.getMediaUrls())           // 미디어 URL
                .build();
    }
}
