package com.question.service.dto.api.response;

import lombok.Data;

@Data
public class AnswerResponseDto {
    private Integer totalQuestions;
    private Integer correctAnswers;
    private Integer score;
}
