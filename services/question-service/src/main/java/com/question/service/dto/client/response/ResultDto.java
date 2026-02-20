package com.question.service.dto.client.response;

import lombok.Data;

@Data
public class ResultDto {
    private Long questionId;
    private Boolean isCorrect;
}
