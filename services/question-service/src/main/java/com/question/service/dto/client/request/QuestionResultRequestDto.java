package com.question.service.dto.client.request;

import lombok.Data;

import java.util.List;

@Data
public class QuestionResultRequestDto {

    private List<QuestionRequest> requests;
}
