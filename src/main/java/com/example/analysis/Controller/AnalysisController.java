package com.example.analysis.Controller;

import com.example.analysis.DTO.DreamResponseDTO;
import com.example.analysis.Service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;


    // 꿈 요약
    @PostMapping("/summarize")  // ← @PostMapping으로 변경!
    public DreamResponseDTO analysis(@RequestBody String dreamText) {

        return analysisService.summarizeText(dreamText);
    }

    // 꿈 해몽
    @GetMapping("/interpret/{dreamId}")
    public DreamResponseDTO interpret(@PathVariable Long dreamId){

        return analysisService.analyzeDream(dreamId);
    }




}
