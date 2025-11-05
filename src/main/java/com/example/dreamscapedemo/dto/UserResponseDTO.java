package com.example.dreamscapedemo.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String profileImage;
}
