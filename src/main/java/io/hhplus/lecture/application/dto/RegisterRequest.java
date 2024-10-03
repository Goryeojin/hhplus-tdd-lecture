package io.hhplus.lecture.application.dto;

import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotNull
        Long lectureId,
        @NotNull
        String studentId
) {
}
