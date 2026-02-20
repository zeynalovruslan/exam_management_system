package com.exam.service.dto.api.request;

import lombok.Data;

@Data
public class AnswerDto {
    private Long questionId;
    private Long optionId;
}
