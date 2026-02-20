package com.question.service.controller;

import com.question.service.dto.api.request.AnswerTotalRequestDto;
import com.question.service.dto.api.response.AnswerResponseDto;
import com.question.service.dto.client.request.QuestionGradeRequestDto;
import com.question.service.dto.client.response.QuestionGradeResponseDto;
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

    @PostMapping("/grade-total")
    public ResponseEntity<AnswerResponseDto> gradeTotalQuestions(@RequestBody AnswerTotalRequestDto request) {
        AnswerResponseDto response = gradingService.gradeTotalQuestions(request);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/grade")
    public ResponseEntity<QuestionGradeResponseDto> gradePerQuestions(@RequestBody QuestionGradeRequestDto request) {
        QuestionGradeResponseDto response = gradingService.gradePerQuestions(request);
        return ResponseEntity.ok(response);
    }

}
