package io.hhplus.lecture.application.dto;

import io.hhplus.lecture.domain.model.Lecture;
import io.hhplus.lecture.domain.model.Registration;
import lombok.Builder;

import java.util.List;

@Builder
public record LectureResponse(
    List<Lecture> lectures,
    List<Registration> registrations
) {
}
