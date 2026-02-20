package com.exam.service.repository;

import com.exam.service.entity.AttemptQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptQuestionRepository extends JpaRepository<AttemptQuestionEntity, Long> {

    List<AttemptQuestionEntity> findByAttemptId(Long attemptId);

}
