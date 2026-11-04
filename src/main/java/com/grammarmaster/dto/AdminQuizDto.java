package com.grammarmaster.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

// Used both as the request body and as the response, so the admin sees correct answers too.
public record AdminQuizDto(
        Long id,
        @NotBlank @Size(max = 255) String title,
        @Size(max = 1000) String description,
        @NotEmpty @Valid List<AdminQuestionDto> questions
) {}
