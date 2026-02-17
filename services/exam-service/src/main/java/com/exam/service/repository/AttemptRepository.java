package com.exam.service.repository;

import com.exam.service.entity.AttemptEntity;
import com.exam.service.enums.AttemptStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptRepository extends JpaRepository<AttemptEntity, Long> {

    boolean existsByUserIdAndStatus(String userId, AttemptStatusEnum status);
}
