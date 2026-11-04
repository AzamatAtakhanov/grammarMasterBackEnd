package com.grammarmaster.service;

import com.grammarmaster.dto.AdminQuestionDto;
import com.grammarmaster.dto.AdminQuizDto;
import com.grammarmaster.model.Question;
import com.grammarmaster.model.Quiz;
import com.grammarmaster.repository.QuestionRepository;
import com.grammarmaster.repository.QuizRepository;
import com.grammarmaster.repository.QuizResultRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdminQuizService {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizResultRepository resultRepository;

    public AdminQuizService(QuizRepository quizRepository, QuestionRepository questionRepository,
                            QuizResultRepository resultRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.resultRepository = resultRepository;
    }

    @Transactional(readOnly = true)
    public List<AdminQuizDto> getAllQuizzes() {
        return quizRepository.findAll().stream()
                .map(quiz -> toDto(quiz, questionRepository.findByQuizIdOrderByIdAsc(quiz.getId())))
                .toList();
    }

    @Transactional
    public AdminQuizDto createQuiz(AdminQuizDto request) {
        Quiz quiz = quizRepository.save(new Quiz(request.title().trim(), blankToNull(request.description())));
        return toDto(quiz, saveQuestions(quiz, request.questions()));
    }

    // Questions are replaced as a whole. Results only store a score, so nothing points at old question ids.
    @Transactional
    public AdminQuizDto updateQuiz(Long id, AdminQuizDto request) {
        Quiz quiz = findQuiz(id);
        quiz.setTitle(request.title().trim());
        quiz.setDescription(blankToNull(request.description()));

        questionRepository.deleteByQuizId(id);
        return toDto(quiz, saveQuestions(quiz, request.questions()));
    }

    @Transactional
    public void deleteQuiz(Long id) {
        Quiz quiz = findQuiz(id);
        resultRepository.deleteByQuizId(id);
        questionRepository.deleteByQuizId(id);
        quizRepository.delete(quiz);
    }

    private Quiz findQuiz(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found"));
    }

    private List<Question> saveQuestions(Quiz quiz, List<AdminQuestionDto> dtos) {
        List<Question> questions = dtos.stream()
                .map(q -> new Question(q.text().trim(), q.optionA().trim(), q.optionB().trim(),
                        q.optionC().trim(), q.optionD().trim(), q.correctOption(),
                        blankToNull(q.explanation()), quiz))
                .toList();
        return questionRepository.saveAll(questions);
    }

    private AdminQuizDto toDto(Quiz quiz, List<Question> questions) {
        List<AdminQuestionDto> questionDtos = questions.stream()
                .map(q -> new AdminQuestionDto(q.getId(), q.getText(), q.getOptionA(), q.getOptionB(),
                        q.getOptionC(), q.getOptionD(), q.getCorrectOption(), q.getExplanation()))
                .toList();
        return new AdminQuizDto(quiz.getId(), quiz.getTitle(), quiz.getDescription(), questionDtos);
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
