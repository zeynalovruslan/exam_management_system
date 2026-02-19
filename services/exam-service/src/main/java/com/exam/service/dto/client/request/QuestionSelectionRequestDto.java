package com.exam.service.dto.client.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
public class QuestionSelectionRequestDto {
    private int count;
    private String topic;
    private String difficulty;


}
