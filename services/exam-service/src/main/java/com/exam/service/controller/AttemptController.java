package com.exam.service.controller;

import com.exam.service.dto.AttemptResponseDto;
import com.exam.service.service.AttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/attempts")
@Validated
public class AttemptController {
    private final AttemptService attemptService;


    @PostMapping("/start")

    public ResponseEntity<AttemptResponseDto> start(){
        AttemptResponseDto response = attemptService.start();
        return ResponseEntity.ok(response);
    }
}
