package com.question.service.dto.api.request;

import lombok.Data;

@Data
public class AnswerItemRequestDto {

    private Long questionId;
    private Long selectedOptionId;
}
