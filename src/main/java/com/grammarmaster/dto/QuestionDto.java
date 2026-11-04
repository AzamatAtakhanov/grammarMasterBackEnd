package com.grammarmaster.dto;

public record QuestionDto(
        Long id,
        String text,
        String optionA,
        String optionB,
        String optionC,
        String optionD
) {}
