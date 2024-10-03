package io.hhplus.lecture.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.hhplus.lecture.api.common.exception.CustomException;
import io.hhplus.lecture.api.common.exception.ErrorCode;
import lombok.Builder;

import java.time.LocalDateTime;

import static io.hhplus.lecture.api.common.exception.ErrorCode.FULL_CAPACITY;

@Builder
public record Lecture (
    Long registrationId,
    Long lectureId,
    String lectureTitle,
    String lecturer,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime lectureDateTime,
    Integer capacity,
    Integer currentCapacity,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime registrationDate,
    String studentId
) {
    public void checkLectureDate() {
        // 신청일이 특강일 이후라면 에러 반환
        if (LocalDateTime.now().isAfter(this.lectureDateTime)) {
            throw new CustomException(ErrorCode.LECTURE_REGISTRATION_CLOSED);
        }
    }

    public void checkCapacity() {
        // 정원이 남지 않았다면 에러 반환
        if (this.currentCapacity >= this.capacity) {
            throw new CustomException(FULL_CAPACITY);
        }
    }

    public Lecture incrementCapacity() {
        return Lecture.builder()
                .lectureId(this.lectureId)
                .lectureTitle(this.lectureTitle)
                .lecturer(this.lecturer)
                .lectureDateTime(this.lectureDateTime)
                .capacity(this.capacity)
                .currentCapacity(this.currentCapacity + 1)
                .build();
    }
}
