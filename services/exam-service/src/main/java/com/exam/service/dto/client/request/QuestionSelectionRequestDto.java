package com.exam.service.dto.client.request;

import com.exam.service.enums.DifficultyEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
public class QuestionSelectionRequestDto {
    private int count;
    private String topic;
    private DifficultyEnum difficulty;
}
