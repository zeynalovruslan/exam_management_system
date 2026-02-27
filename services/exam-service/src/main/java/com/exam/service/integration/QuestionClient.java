package com.exam.service.integration;


import com.exam.service.config.FeignAuthForwardConfig;
import com.exam.service.dto.client.request.QuestionGradeRequestDto;
import com.exam.service.dto.client.request.QuestionResultRequestDto;
import com.exam.service.dto.client.request.QuestionSelectionRequestDto;
import com.exam.service.dto.client.response.QuestionGradeResponseDto;
import com.exam.service.dto.client.response.QuestionResultResponseDto;
import com.exam.service.dto.client.response.QuestionSelectionResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "question-service",
        url = "${services.question-service.base-url}",
        configuration = FeignAuthForwardConfig.class
)
public interface QuestionClient {

    @PostMapping("/api/questions")
    List<QuestionSelectionResponseDto> selectQuestions(@RequestBody QuestionSelectionRequestDto requestDto);

    @PostMapping("/api/questions/grade")
    QuestionGradeResponseDto grade(@RequestBody QuestionGradeRequestDto request);


    @PostMapping("/api/questions/result")
    QuestionResultResponseDto getResult(@RequestBody QuestionResultRequestDto request);
}
