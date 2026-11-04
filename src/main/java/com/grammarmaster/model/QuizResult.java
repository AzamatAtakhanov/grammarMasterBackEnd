package com.grammarmaster.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_results")
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String studentName;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private int score;
    private int totalQuestions;

    @Column(nullable = false)
    private LocalDateTime completedAt;

    public QuizResult() {}

    public QuizResult(String studentName, Quiz quiz, int score, int totalQuestions) {
        this.studentName = studentName;
        this.quiz = quiz;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.completedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getStudentName() { return studentName; }
    public Quiz getQuiz() { return quiz; }
    public int getScore() { return score; }
    public int getTotalQuestions() { return totalQuestions; }
    public LocalDateTime getCompletedAt() { return completedAt; }
}
