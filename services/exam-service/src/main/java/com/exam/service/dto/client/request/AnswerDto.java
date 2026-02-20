package com.exam.service.dto.client.request;

import lombok.Data;

@Data
public class AnswerDto {
    private Long questionId;
    private Long selectedOptionId;
}
