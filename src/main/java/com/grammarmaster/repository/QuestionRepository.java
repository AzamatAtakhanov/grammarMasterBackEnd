package com.grammarmaster.repository;

import com.grammarmaster.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByQuizIdOrderByIdAsc(Long quizId);

    void deleteByQuizId(Long quizId);
}
