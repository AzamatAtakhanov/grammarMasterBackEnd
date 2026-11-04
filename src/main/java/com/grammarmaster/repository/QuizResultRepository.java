package com.grammarmaster.repository;

import com.grammarmaster.model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    List<QuizResult> findAllByOrderByCompletedAtDesc();

    void deleteByQuizId(Long quizId);
}
