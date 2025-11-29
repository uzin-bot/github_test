package com.example.dreamscapedemo.dto;

import com.example.dreamscapedemo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Long userId;
    private String userNickName;
    private String name;
    private String email;
    private String message;
    private String profileImage;
    private String socialProvider;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // User 엔티티에서 UserResponseDTO로 변환하는 from() 메서드
    public static UserResponseDTO from(User user) {
        return UserResponseDTO.builder()
                .userId(user.getId())
                .userNickName(user.getUserNickName())
                .name(user.getName())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .socialProvider(user.getSocialProvider())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
