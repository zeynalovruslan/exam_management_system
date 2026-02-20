package com.exam.service.dto.client.request;

import lombok.Data;

import java.util.List;

@Data
public class QuestionGradeRequestDto {

    private List<AnswerDto> answers;
}
