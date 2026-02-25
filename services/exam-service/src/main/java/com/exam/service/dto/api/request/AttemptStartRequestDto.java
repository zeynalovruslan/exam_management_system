package com.exam.service.dto.api.request;

import com.exam.service.enums.DifficultyEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AttemptStartRequestDto {

    @NotBlank
    private String topic;

    @NotBlank
    private DifficultyEnum difficulty;
}

