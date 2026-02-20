package com.question.service.dto.api.request;

import lombok.Data;

import java.util.List;

@Data
public class AnswerTotalRequestDto {
    private List<AnswerItemRequestDto> answers;
}
