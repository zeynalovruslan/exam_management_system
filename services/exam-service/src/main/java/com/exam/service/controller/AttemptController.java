package com.exam.service.controller;

import com.exam.service.dto.api.request.AttemptStartRequestDto;
import com.exam.service.dto.api.response.AttemptStartResponseDto;
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
    public ResponseEntity<AttemptStartResponseDto> start(@RequestHeader("Authorization") String authorization,
                                                         @RequestBody AttemptStartRequestDto requestDto) {
        AttemptStartResponseDto response = attemptService.start(authorization, requestDto);
        return ResponseEntity.ok(response);
    }
}
