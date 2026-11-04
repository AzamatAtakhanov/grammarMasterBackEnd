package com.grammarmaster.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SubmitQuizRequest(
        @NotBlank String studentName,
        @NotEmpty List<Answer> answers
) {
    public record Answer(@NotNull Long questionId, @NotBlank String selectedOption) {}
}
