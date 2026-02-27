package com.exam.service.controller;

import com.exam.service.dto.api.request.AttemptStartRequestDto;
import com.exam.service.dto.api.request.AttemptSubmitRequestDto;
import com.exam.service.dto.api.response.AttemptResultResponseDto;
import com.exam.service.dto.api.response.AttemptStartResponseDto;
import com.exam.service.dto.api.response.AttemptSubmitResponseDto;
import com.exam.service.service.AttemptService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/attempts")
@Validated
public class AttemptController {

    private final AttemptService attemptService;

    public AttemptController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/start")
    public ResponseEntity<AttemptStartResponseDto> start(
            @RequestBody AttemptStartRequestDto requestDto) {
        return ResponseEntity.ok(attemptService.start(requestDto));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/submit")
    public ResponseEntity<AttemptSubmitResponseDto> submit(@RequestBody AttemptSubmitRequestDto requestDto) {
        return ResponseEntity.ok(attemptService.submit(requestDto));
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/results/{attemptId}")
    public ResponseEntity<AttemptResultResponseDto> getResult(@PathVariable Long attemptId) {
        return ResponseEntity.ok(attemptService.getResult(attemptId));
    }

}
