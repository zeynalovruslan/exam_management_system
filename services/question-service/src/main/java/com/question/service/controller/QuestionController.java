package com.question.service.controller;

import com.question.service.dto.client.request.QuestionSelectionRequestDto;
import com.question.service.dto.client.response.QuestionResponseDto;
import com.question.service.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/questions")
@Validated
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<List<QuestionResponseDto>> select(@RequestBody QuestionSelectionRequestDto request) {
       List <QuestionResponseDto> response = questionService.selectQuestion(request);
        return ResponseEntity.ok(response);
    }
}
