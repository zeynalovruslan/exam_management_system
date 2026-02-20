package com.exam.service.dto.api.request;

import lombok.Data;

import java.util.List;

@Data
public class AttemptSubmitRequestDto {

    private List<AnswerDto> answers;

}
