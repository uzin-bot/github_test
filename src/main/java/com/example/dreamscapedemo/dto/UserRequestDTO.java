package com.example.dreamscapedemo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRequestDTO {
    @NotBlank private String name;
    @Email @NotBlank private String email;
    private String profileImage;
}
