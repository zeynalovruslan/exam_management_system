package com.exam.service.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class AttemptResponseDto {

    private Long id;
    private String status;
    private Instant startedAt;
}
