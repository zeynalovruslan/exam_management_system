package com.question.service.controller;

import com.question.service.dto.client.request.QuestionResultRequestDto;
import com.question.service.dto.client.request.QuestionSelectionRequestDto;
import com.question.service.dto.client.response.QuestionResultResponseDto;
import com.question.service.dto.client.response.QuestionSelectionResponseDto;
import com.question.service.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/questions")
@Validated
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<List<QuestionSelectionResponseDto>> select(@RequestBody QuestionSelectionRequestDto request) {
        return ResponseEntity.ok(questionService.selectQuestion(request));
    }


    @PostMapping("/result")
    public ResponseEntity<QuestionResultResponseDto> getResult(@RequestBody QuestionResultRequestDto requestDto) {
        return ResponseEntity.ok(questionService.getResult(requestDto));
    }
}
