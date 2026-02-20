package com.question.service.controller;

import com.question.service.dto.api.request.AnswerTotalRequestDto;
import com.question.service.dto.api.response.AnswerResponseDto;
import com.question.service.service.GradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/questions")
@Validated
public class GradingController {

    private final GradingService gradingService;

    @PostMapping("/grade")
    public ResponseEntity<AnswerResponseDto> grade(@RequestBody AnswerTotalRequestDto request) {
        AnswerResponseDto response = gradingService.gradeAnswer(request);
        return ResponseEntity.ok(response);
    }
}
