package com.question.service.dto.api.response;

import lombok.Data;

@Data
public class AnswerResponseDto {
    private int totalQuestions;
    private int correctAnswers;
    private int score;
}
