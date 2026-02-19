package com.question.service.mapper;

import com.question.service.dto.client.response.QuestionResponseDto;
import com.question.service.entity.QuestionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionResponseDto toResponse(QuestionEntity entity);

}
