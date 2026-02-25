package com.exam.service.dto.client.request;

import lombok.Data;

@Data
public class QuestionRequest {

    private Long questionId;
    private Long selectedOptionId;
}
