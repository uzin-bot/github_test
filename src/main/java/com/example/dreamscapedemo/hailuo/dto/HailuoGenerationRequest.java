package com.example.dreamscapedemo.hailuo.dto;

public record HailuoGenerationRequest(Input input, String webhookUrl) {
    public record Input(String prompt, String image) {}
}
