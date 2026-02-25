package com.exam.service.dto.api.response;

import com.exam.service.dto.client.response.QuestionResultResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class AttemptResultResponseDto {

    private Integer totalQuestions;
    private Integer correctAnswerCount;
    private Integer wrongAnswerCount;
    private Integer score;

    private List<ResultResponse> questions;
}
