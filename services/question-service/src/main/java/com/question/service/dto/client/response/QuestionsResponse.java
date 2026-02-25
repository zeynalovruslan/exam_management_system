package com.question.service.dto.client.response;

import lombok.Data;

@Data
public class QuestionsResponse {

    private Long questionId;
    private String questionText;

    private Long correctAnswerId;
    private String correctAnswerText;

    private Long selectedOptionId;
    private String selectedOptionText;
}
