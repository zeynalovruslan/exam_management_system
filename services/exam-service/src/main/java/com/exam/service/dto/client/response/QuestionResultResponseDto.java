package com.exam.service.dto.client.response;

import lombok.Data;

import java.util.List;

@Data
public class QuestionResultResponseDto {

    private Integer totalQuestions;
    private Integer score;
    private Integer correctAnswer;
    private Integer wrongAnswer;

    private List<QuestionResponse> questions;
}
