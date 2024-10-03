package io.hhplus.lecture.domain.service;

import io.hhplus.lecture.api.common.exception.CustomException;
import io.hhplus.lecture.api.common.exception.ErrorCode;
import io.hhplus.lecture.domain.model.Lecture;
import io.hhplus.lecture.domain.repository.LectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureService lectureService;

    private static final Long LECTURE_ID = 1L;
    private static final String STUDENT_ID = "test";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 날짜별_신청가능한_특강_목록을_조회한다() {
        // given
        LocalDate lectureDate = LocalDate.of(2024, 10, 4);
        List<Lecture> mockLectures = List.of(mock(Lecture.class), mock(Lecture.class));

        when(lectureRepository.findAllAvailableByDate(lectureDate)).thenReturn(mockLectures);

        // when
        List<Lecture> result = lectureService.lecturesAvailable(lectureDate);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(lectureRepository).findAllAvailableByDate(lectureDate);
    }

    @Test
    public void 특정_특강이_존재하지_않을_때_조회_실패() {
        // given
        when(lectureRepository.findById(LECTURE_ID))
                .thenThrow(new CustomException(ErrorCode.LECTURE_NOT_FOUND));

        // when - then
        assertThatThrownBy(() -> lectureService.checkStatus(LECTURE_ID))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.LECTURE_NOT_FOUND.getMessage());

        verify(lectureRepository).findById(LECTURE_ID); // 메서드 호출했는지 검증
    }

    @Test
    void 특정_특강_조회_및_수용_인원_확인() {
        // given
        Lecture mockLecture = Lecture.builder()
                .lectureId(LECTURE_ID)
                .lectureTitle("test")
                .capacity(30)
                .currentCapacity(29)
                .build();
        given(lectureRepository.findById(LECTURE_ID)).willReturn(mockLecture);

        // when
        Lecture result = lectureService.checkStatus(LECTURE_ID);

        // then
        assertThat(result).isNotNull();
        assertThat(result.lectureId()).isEqualTo(LECTURE_ID);
        assertThat(result.lectureTitle()).isEqualTo("test");

        verify(lectureRepository).findById(LECTURE_ID);
    }

    @Test
    void 특정_특강의_수용_인원이_꽉_찬_경우_예외_발생() {
        // given
        Lecture mockLecture = mock(Lecture.class);
        when(lectureRepository.findById(LECTURE_ID)).thenReturn(mockLecture);

        doThrow(new CustomException(ErrorCode.FULL_CAPACITY))
                .when(mockLecture).checkCapacity();

        // when
        CustomException exception = assertThrows(CustomException.class, () -> {
            lectureService.checkStatus(LECTURE_ID);
        });

        // then
        assertEquals(ErrorCode.FULL_CAPACITY, exception.getErrorCode());
        verify(mockLecture).checkCapacity();
    }

    @Test
    void 특강의_인원_정보가_없다면_상태_변경에_실패한다() {
        // given
        Lecture mockLecture = mock(Lecture.class);
        Lecture updatedLecture = mock(Lecture.class);

        when(mockLecture.incrementCapacity()).thenReturn(updatedLecture);
        doThrow(new CustomException(ErrorCode.LECTURE_CAPACITY_NOT_FOUND))
                .when(lectureRepository).incrementCapacity(updatedLecture);

        // when
        CustomException exception = assertThrows(CustomException.class, () -> lectureService.incrementCapacity(mockLecture));

        // then
        verify(mockLecture).incrementCapacity();
        assertEquals(ErrorCode.LECTURE_CAPACITY_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 특정_특강의_수용_인원을_증가시킨다() {
        // given
        Lecture mockLecture = mock(Lecture.class);
        Lecture updatedLecture = mock(Lecture.class);

        when(mockLecture.incrementCapacity()).thenReturn(updatedLecture);

        // when
        lectureService.incrementCapacity(mockLecture);

        // then
        verify(mockLecture).incrementCapacity();
        verify(lectureRepository).incrementCapacity(updatedLecture);
    }
}