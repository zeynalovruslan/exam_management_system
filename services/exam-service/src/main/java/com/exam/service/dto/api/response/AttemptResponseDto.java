package com.exam.service.dto.api.response;

import com.exam.service.dto.client.response.QuestionResponseDto;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class AttemptResponseDto {

    private Long id;
    private String status;
    private Instant startedAt;
    private List<QuestionResponseDto> questions;
}
