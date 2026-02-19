package com.question.service.dto.client.request;

import com.question.service.enums.DifficultyEnum;
import lombok.Data;

@Data
public class QuestionSelectionRequestDto {
    private int count;
    private String topic;
    private DifficultyEnum difficulty;

}
