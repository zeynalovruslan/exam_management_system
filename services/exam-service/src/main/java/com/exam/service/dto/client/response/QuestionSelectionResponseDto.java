package com.exam.service.dto.client.response;

import lombok.Data;

import java.util.List;

@Data
public class QuestionSelectionResponseDto {
    private Long id;
    private String text;
    private List<OptionDto> options;
}
