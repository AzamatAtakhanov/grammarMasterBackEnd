package com.grammarmaster.dto;

import java.time.LocalDateTime;
import java.util.List;

public record QuizResultDto(
        Long resultId,
        String studentName,
        String quizTitle,
        int score,
        int totalQuestions,
        double percentage,
        LocalDateTime completedAt,
        List<QuestionFeedbackDto> feedback
) {
    public record QuestionFeedbackDto(
            Long questionId,
            String question,
            String selectedOption,
            String correctOption,
            boolean correct,
            String explanation
    ) {}
}
