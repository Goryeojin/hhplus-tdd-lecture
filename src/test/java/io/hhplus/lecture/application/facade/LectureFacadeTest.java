package io.hhplus.lecture.application.facade;

import io.hhplus.lecture.application.dto.LectureResponse;
import io.hhplus.lecture.domain.model.Lecture;
import io.hhplus.lecture.domain.service.LectureService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LectureFacadeTest {
    @InjectMocks
    private LectureFacade lectureFacade; // 테스트 대상 객체 주입

    @Mock
    private LectureService lectureService;

    @Test
    @DisplayName("신청 가능한 날짜별 특강 목록을 조회한다.")
    void returnAvailableLecturesByDate() {
        // given
        LocalDate lectureDate = LocalDate.of(2024, 10, 3);
        List<Lecture> mockLectures = Collections.singletonList(mock(Lecture.class));
        when(lectureService.lecturesAvailable(lectureDate)).thenReturn(mockLectures);

        // when
        LectureResponse response = lectureFacade.lecturesAvailable(lectureDate);

        //then
        assertThat(response).isNotNull();
        assertThat(response.lectures()).hasSize(1);
        verify(lectureService).lecturesAvailable(lectureDate);
    }
}