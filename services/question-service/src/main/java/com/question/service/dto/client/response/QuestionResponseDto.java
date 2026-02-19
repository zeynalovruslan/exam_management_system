package com.question.service.dto.client.response;

import lombok.Data;

import java.util.List;

@Data
public class QuestionResponseDto {
    private Long id;
    private String text;
    private List<OptionDto> options;

}
