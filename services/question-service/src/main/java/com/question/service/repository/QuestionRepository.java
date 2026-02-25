package com.question.service.repository;

import com.question.service.entity.QuestionEntity;
import com.question.service.enums.DifficultyEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    @Query(value = """
        select q.id
        from questions q
        where q.topic = :topic and q.difficulty = :difficulty
        order by rand()
        limit :count
        """, nativeQuery = true)
    List<Long> findRandomId(@Param("topic") String topic,
                            @Param("difficulty") String difficulty,
                            @Param("count") int count);


    @Query("""
                select distinct q
                from QuestionEntity q
                left join fetch q.options
                where q.id in :ids
            """)
    Optional<List<QuestionEntity>> findAllByIdInWithOptions(@Param("ids") List<Long> ids);


}
