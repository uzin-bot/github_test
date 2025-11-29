package com.example.dreamscapedemo.controller;

import com.example.dreamscapedemo.service.MediaGenerationService;
import com.example.dreamscapedemo.dto.MediaResponseDTO;
import com.example.dreamscapedemo.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    // ✅ Hailuo 연동 서비스 (이미 구현해둔 generateForDream 사용)
    private final MediaGenerationService mediaGenerationService;

    @GetMapping("/ping")
    public String ping() {
        return "media ok";
    }

    // 더미 생성 (기존)
    @PostMapping("/test/{dreamId}")
    public ResponseEntity<MediaResponseDTO> createDummyMedia(@PathVariable Long dreamId) {
        MediaResponseDTO response = mediaService.createDummyMedia(dreamId);
        return ResponseEntity.ok(response);
    }

    // ✅ 실제 Hailuo 호출 엔드포인트
    // POST /api/media/{dreamId}/generate?prompt=...
    @PostMapping("/{dreamId}/generate")
    public ResponseEntity<?> generate(
            @PathVariable Long dreamId,
            @RequestParam(value = "prompt", required = false) String promptParam,
            @RequestBody(required = false) Map<String, Object> body
    ) {
        // 1) 우선순위: 쿼리스트링 → 2) Body(JSON)
        String prompt = promptParam;
        if ((prompt == null || prompt.isBlank()) && body != null) {
            Object p = body.get("prompt");
            if (p != null) prompt = String.valueOf(p);
        }

        if (prompt == null || prompt.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "prompt is required",
                    "hint", "use query ?prompt=... or JSON body {\"prompt\":\"...\"}"
            ));
        }

        var media = mediaGenerationService.generateForDream(dreamId, prompt);
        return ResponseEntity.ok(Map.of(
                "id", media.getId(),
                "dreamId", media.getDream().getId(),
                "status", media.getStatus(),
                "mediaType", media.getMediaType(),
                "url", media.getUrl()
        ));
    }
}
