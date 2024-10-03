package io.hhplus.lecture.domain.model;

import lombok.Builder;

@Builder
public record LectureCapacity(
        Long id,
        Long lectureId,
        int capacity,
        int currentCount
) {
}
