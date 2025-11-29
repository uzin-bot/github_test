package com.example.dreamscapedemo.service;

import com.example.dreamscapedemo.dto.DreamRequestDTO;
import com.example.dreamscapedemo.dto.DreamResponseDTO;

import java.util.List;


// DreamService 꿈(Dream) CRUD를 정의하는 인터페이스
public interface DreamService {

    // C: 특정 유저의 꿈 생성
    DreamResponseDTO createDream(Long userId, DreamRequestDTO dto);

    // R: 한 개 꿈 조회
    DreamResponseDTO getDream(Long dreamId);

    // R: 유저별 꿈 목록 조회
    List<DreamResponseDTO> getDreamsByUser(Long userId);

    // U: 꿈 수정
    DreamResponseDTO updateDream(Long dreamId, DreamRequestDTO dto);

    // D: 꿈 삭제
    void deleteDream(Long dreamId);
}
