package com.grammarmaster.dto;

import java.util.List;

public record QuizDetailsDto(Long id, String title, String description, List<QuestionDto> questions) {}
