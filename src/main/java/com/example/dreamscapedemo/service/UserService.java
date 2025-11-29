package com.example.dreamscapedemo.service;

import com.example.dreamscapedemo.dto.UserRequestDTO;
import com.example.dreamscapedemo.dto.UserResponseDTO;

public interface UserService {

    // 회원가입
    UserResponseDTO signup(UserRequestDTO dto);

    // 로그인
    UserResponseDTO login(String email, String password);

    // 회원정보 조회
    UserResponseDTO getUserById(Long userId);

    // 회원 삭제
    void deleteUser(Long userId);
}
