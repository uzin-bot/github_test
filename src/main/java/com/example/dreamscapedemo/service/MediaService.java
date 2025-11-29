package com.example.dreamscapedemo.service;

import com.example.dreamscapedemo.dto.MediaResponseDTO;

public interface MediaService {

    // 1차 구현
    MediaResponseDTO createDummyMedia(Long dreamId);
}
