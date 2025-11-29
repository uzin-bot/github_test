package com.example.dreamscapedemo.hailuo;

import com.example.dreamscapedemo.hailuo.dto.HailuoGenerationRequest;
import com.example.dreamscapedemo.hailuo.dto.HailuoGenerationResponse;
import com.example.dreamscapedemo.hailuo.dto.HailuoTaskStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HailuoClient {
    private final WebClient hailuoWebClient;
    private final HailuoProps props;
    private final ObjectMapper om = new ObjectMapper();

    public HailuoGenerationResponse submit(String prompt, String imageUrl) {
        var body = new HailuoGenerationRequest(
                new HailuoGenerationRequest.Input(prompt, imageUrl),
                props.webhookUrl()
        );

        return hailuoWebClient.post()
                .uri(props.generationPath())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                // ↓↓↓ 에러일 때 응답 바디를 로그/예외로 전달
                .exchangeToMono(resp -> {
                    if (resp.statusCode().is2xxSuccessful()) {
                        return resp.bodyToMono(HailuoGenerationResponse.class);
                    } else {
                        return resp.bodyToMono(String.class)
                                .defaultIfEmpty("")  // 바디 없을 수도 있음
                                .flatMap(err -> Mono.error(new RuntimeException(
                                        "Hailuo " + resp.statusCode() + " :: " + err
                                )));
                    }
                })
                .block();
    }

    public HailuoTaskStatus getStatus(String taskId) {
        var resp = hailuoWebClient.post()
                .uri(props.taskStatusPath())
                .bodyValue(Map.of("taskId", taskId))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        String status = text(resp, "status");
        String url = extractUrl(resp);

        return new HailuoTaskStatus(
                status,
                url,
                om.convertValue(resp, new TypeReference<Map<String, Object>>() {})
        );
    }

    private String text(JsonNode n, String f) {
        return n != null && n.has(f) && !n.get(f).isNull() ? n.get(f).asText() : null;
    }

    private String extractUrl(JsonNode root) {
        if (root == null) return null;
        for (var k : List.of("url", "videoUrl", "resultUrl")) {
            if (root.has(k) && !root.get(k).isNull()) return root.get(k).asText();
        }
        if (root.has("result")) {
            JsonNode r = root.get("result");
            for (var k : List.of("url", "videoUrl")) {
                if (r.has(k) && !r.get(k).isNull()) return r.get(k).asText();
            }
        }
        if (root.has("outputs") && root.get("outputs").isArray() && root.get("outputs").size() > 0) {
            JsonNode first = root.get("outputs").get(0);
            if (first.has("url")) return first.get("url").asText();
        }
        return null;
    }
}