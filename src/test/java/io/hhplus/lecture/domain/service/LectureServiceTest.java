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
}