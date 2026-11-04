package com.grammarmaster.controller;

import com.grammarmaster.dto.*;
import com.grammarmaster.model.Quiz;
import com.grammarmaster.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @GetMapping("/{id}")
    public QuizDetailsDto getQuiz(@PathVariable Long id) {
        return quizService.getQuiz(id);
    }

    @PostMapping("/{id}/submit")
    public QuizResultDto submitQuiz(@PathVariable Long id,
                                    @Valid @RequestBody SubmitQuizRequest request) {
        return quizService.submitQuiz(id, request);
    }
}
