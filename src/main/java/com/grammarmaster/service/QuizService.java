package com.grammarmaster.service;

import com.grammarmaster.dto.*;
import com.grammarmaster.model.*;
import com.grammarmaster.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizResultRepository resultRepository;

    public QuizService(QuizRepository quizRepository, QuestionRepository questionRepository,
                       QuizResultRepository resultRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.resultRepository = resultRepository;
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @Transactional(readOnly = true)
    public QuizDetailsDto getQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found"));

        List<QuestionDto> questions = questionRepository.findByQuizIdOrderByIdAsc(quizId)
                .stream()
                .map(q -> new QuestionDto(q.getId(), q.getText(), q.getOptionA(),
                        q.getOptionB(), q.getOptionC(), q.getOptionD()))
                .toList();

        return new QuizDetailsDto(quiz.getId(), quiz.getTitle(), quiz.getDescription(), questions);
    }

    @Transactional
    public QuizResultDto submitQuiz(Long quizId, SubmitQuizRequest request) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found"));

        List<Question> questions = questionRepository.findByQuizIdOrderByIdAsc(quizId);
        if (questions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This quiz has no questions");
        }

        Map<Long, String> submitted = new HashMap<>();
        for (SubmitQuizRequest.Answer answer : request.answers()) {
            String normalized = answer.selectedOption().trim().toUpperCase(Locale.ROOT);
            if (!Set.of("A", "B", "C", "D").contains(normalized)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "selectedOption must be A, B, C or D");
            }
            submitted.put(answer.questionId(), normalized);
        }

        List<QuizResultDto.QuestionFeedbackDto> feedback = new ArrayList<>();
        int score = 0;

        for (Question question : questions) {
            String selected = submitted.getOrDefault(question.getId(), "");
            boolean correct = selected.equalsIgnoreCase(question.getCorrectOption());
            if (correct) score++;

            feedback.add(new QuizResultDto.QuestionFeedbackDto(
                    question.getId(), question.getText(), selected,
                    question.getCorrectOption(), correct, question.getExplanation()
            ));
        }

        QuizResult saved = resultRepository.save(
                new QuizResult(request.studentName().trim(), quiz, score, questions.size()));

        double percentage = Math.round((score * 1000.0 / questions.size())) / 10.0;
        return new QuizResultDto(saved.getId(), saved.getStudentName(), quiz.getTitle(),
                score, questions.size(), percentage, saved.getCompletedAt(), feedback);
    }

    public List<QuizResult> getResults() {
        return resultRepository.findAllByOrderByCompletedAtDesc();
    }
}
