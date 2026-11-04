package com.grammarmaster.controller;

import com.grammarmaster.config.AdminAuth;
import com.grammarmaster.dto.AdminQuizDto;
import com.grammarmaster.service.AdminQuizService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminAuth adminAuth;
    private final AdminQuizService quizService;

    public AdminController(AdminAuth adminAuth, AdminQuizService quizService) {
        this.adminAuth = adminAuth;
        this.quizService = quizService;
    }

    public record LoginRequest(@NotBlank String password) {}

    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody LoginRequest request) {
        return Map.of("token", adminAuth.login(request.password()));
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest request) {
        adminAuth.logout(request);
    }

    @GetMapping("/quizzes")
    public List<AdminQuizDto> getQuizzes() {
        return quizService.getAllQuizzes();
    }

    @PostMapping("/quizzes")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminQuizDto createQuiz(@Valid @RequestBody AdminQuizDto request) {
        return quizService.createQuiz(request);
    }

    @PutMapping("/quizzes/{id}")
    public AdminQuizDto updateQuiz(@PathVariable Long id, @Valid @RequestBody AdminQuizDto request) {
        return quizService.updateQuiz(id, request);
    }

    @DeleteMapping("/quizzes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
    }
}
