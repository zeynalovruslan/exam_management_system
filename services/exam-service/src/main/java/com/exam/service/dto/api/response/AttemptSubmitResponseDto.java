package com.exam.service.dto.api.response;

import com.exam.service.dto.client.response.ResultDto;
import lombok.Data;

import java.util.List;

@Data
public class AttemptSubmitResponseDto {
    private  Long attemptId;
    private Integer totalQuestions;
    private Integer correctAnswerCount;
    private Integer wrongAnswerCount;
    private List<ResultDto> results;

}
