package io.hhplus.lecture.domain.model;

import io.hhplus.lecture.support.exception.CustomException;
import io.hhplus.lecture.support.exception.ErrorCode;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class LectureTest {
    private static final Long LECTURE_ID = 1L;
    private static final String LECTURE_TITLE = "Lecture";
    private static final String LECTURER = "Lecturer";
    private static final LocalDateTime LECTURE_DATETIME = LocalDateTime.now().plusDays(1); // 미래의 날짜
    private static final Integer CAPACITY = 30;

    @Test
    void 신청일이_특강일_이후일_경우_예외_발생() {
        // given
        Lecture lecture = Lecture.builder()
                .lectureId(LECTURE_ID)
                .lectureTitle(LECTURE_TITLE)
                .lecturer(LECTURER)
                .lectureDateTime(LocalDateTime.now().minusDays(1)) // 과거의 날짜
                .capacity(CAPACITY)
                .currentCapacity(0)
                .build();

        // when - then
        assertThatExceptionOfType(CustomException.class)
                .isThrownBy(lecture::checkLectureDate)
                .withMessage(ErrorCode.LECTURE_REGISTRATION_CLOSED.getMessage());
    }

    @Test
    void 현재_정원이_최대_정원에_도달한_경우_예외_발생() {
        // given
        Lecture lecture = Lecture.builder()
                .lectureId(LECTURE_ID)
                .lectureTitle(LECTURE_TITLE)
                .lecturer(LECTURER)
                .lectureDateTime(LECTURE_DATETIME)
                .capacity(CAPACITY)
                .currentCapacity(CAPACITY) // 현재 정원이 최대와 같음
                .build();

        // when - then
        assertThatExceptionOfType(CustomException.class)
                .isThrownBy(lecture::checkCapacity)
                .withMessage(ErrorCode.FULL_CAPACITY.getMessage());
    }

    @Test
    void 정원이_남아있는_경우_성공() {
        // given
        Lecture lecture = Lecture.builder()
                .lectureId(LECTURE_ID)
                .lectureTitle(LECTURE_TITLE)
                .lecturer(LECTURER)
                .lectureDateTime(LECTURE_DATETIME)
                .capacity(CAPACITY)
                .currentCapacity(CAPACITY - 1) // 현재 정원이 최대보다 하나 적음
                .build();

        // when & then
        assertThatCode(lecture::checkCapacity).doesNotThrowAnyException();
    }

    @Test
    void 현재_정원을_증가시킨다() {
        // given
        Lecture lecture = Lecture.builder()
                .lectureId(LECTURE_ID)
                .lectureTitle(LECTURE_TITLE)
                .lecturer(LECTURER)
                .lectureDateTime(LECTURE_DATETIME)
                .capacity(CAPACITY)
                .currentCapacity(10) // 현재 정원이 10
                .build();

        // when
        Lecture updatedLecture = lecture.incrementCapacity();

        // then
        assertThat(updatedLecture.currentCapacity()).isEqualTo(11); // 현재 정원이 11로 증가해야 함
        assertThat(updatedLecture.capacity()).isEqualTo(CAPACITY); // 정원은 그대로
    }
}