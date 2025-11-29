package com.example.dreamscapedemo.hailuo.dto;

import java.util.Map;

public record HailuoTaskStatus(String status, String url, Map<String, Object> raw) {}
