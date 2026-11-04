package com.grammarmaster.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdminQuestionDto(
        Long id,
        @NotBlank @Size(max = 1000) String text,
        @NotBlank @Size(max = 255) String optionA,
        @NotBlank @Size(max = 255) String optionB,
        @NotBlank @Size(max = 255) String optionC,
        @NotBlank @Size(max = 255) String optionD,
        @NotBlank @Pattern(regexp = "[A-D]", message = "correctOption must be A, B, C or D") String correctOption,
        @Size(max = 1000) String explanation
) {}
