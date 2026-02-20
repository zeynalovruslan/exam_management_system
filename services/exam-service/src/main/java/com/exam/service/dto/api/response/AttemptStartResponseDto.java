package com.exam.service.dto.api.response;

import com.exam.service.dto.client.response.QuestionSelectionResponseDto;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class AttemptStartResponseDto {

    private Long id;
    private String status;
    private Instant startedAt;
    private List<QuestionSelectionResponseDto> questions;
}
