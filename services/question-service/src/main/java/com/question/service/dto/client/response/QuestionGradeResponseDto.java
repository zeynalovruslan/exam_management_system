package com.question.service.dto.client.response;

import lombok.Data;

import java.util.List;

@Data
public class QuestionGradeResponseDto {
    private Integer correctAnswerCount;
    private List<ResultDto> results;
}
