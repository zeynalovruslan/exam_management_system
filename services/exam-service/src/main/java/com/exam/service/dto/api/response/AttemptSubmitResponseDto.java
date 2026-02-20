package com.exam.service.dto.api.response;

import lombok.Data;

@Data
public class AttemptSubmitResponseDto {
    private  Long attemptId;
    private Integer totalQuestions;
    private Integer correctAnswerCount;
    private Integer wrongAnswerCount;


}
