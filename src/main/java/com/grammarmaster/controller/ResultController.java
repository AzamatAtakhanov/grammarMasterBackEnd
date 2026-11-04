package com.grammarmaster.controller;

import com.grammarmaster.model.QuizResult;
import com.grammarmaster.service.QuizService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {
    private final QuizService quizService;

    public ResultController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public List<QuizResult> getResults() {
        return quizService.getResults();
    }
}
