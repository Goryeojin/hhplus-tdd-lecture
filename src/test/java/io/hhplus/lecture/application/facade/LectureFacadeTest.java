package io.hhplus.lecture.application.facade;

import io.hhplus.lecture.api.common.exception.CustomException;
import io.hhplus.lecture.api.common.exception.ErrorCode;
import io.hhplus.lecture.application.dto.LectureResponse;
import io.hhplus.lecture.application.dto.RegisterRequest;
import io.hhplus.lecture.domain.model.Lecture;
import io.hhplus.lecture.domain.service.LectureService;
import io.hhplus.lecture.domain.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LectureFacadeTest {
    @InjectMocks
    private LectureFacade lectureFacade; // 테스트 대상 객체 주입

    @Mock
    private LectureService lectureService;
    @Mock
    private RegistrationService registrationService;

    private static final Long LECTURE_ID = 1L;
    private static final String STUDENT_ID = "test";
    private RegisterRequest request;
    private Lecture lecture;

    @BeforeEach
    void setUp() {
        request = new RegisterRequest(LECTURE_ID, STUDENT_ID);

        lecture = Lecture.builder()
                .lectureId(1L)
                .lectureDateTime(LocalDateTime.now())
                .lectureTitle("자바 프로그래밍")
                .lecturer("김강사")
                .build();
    }

    @Test
    @DisplayName("특정 특강 신청에 이미 등록된 학생이 요청할 때 신청이 실패한다.")
    void failToRegisterIfStudentAlreadyRegistration() {
        // given
        doThrow(new CustomException(ErrorCode.ALREADY_REGISTERED))
                .when(registrationService).checkRegistered(LECTURE_ID, STUDENT_ID); // 이미 신청했다고 가정

        // when - then
        assertThatThrownBy(() -> lectureFacade.register(request)) // 검증할 메서드 실행
                .isInstanceOf(CustomException.class) // 예외 발생
                .hasMessage(ErrorCode.ALREADY_REGISTERED.getMessage());

        // 이후 메서드들이 실행되지 않았는지 확인
        verify(lectureService, never()).checkStatus(anyLong());
        verify(registrationService, never()).register(anyLong(), anyString());
        verify(lectureService, never()).incrementCapacity(any());
    }

    @Test
    @DisplayName("특정 특강의 신청 수용 인원이 다 차있다면 신청이 실패한다.")
    void failToRegisterIfLectureIsFull() {
        // given
        doNothing().when(registrationService).checkRegistered(LECTURE_ID, STUDENT_ID);
        doThrow(new CustomException(ErrorCode.FULL_CAPACITY))
                .when(lectureService).checkStatus(1L);

        // when
        CustomException exception = assertThrows(CustomException.class, () -> {
            lectureFacade.register(request);
        });

        // then
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.FULL_CAPACITY);
        verify(registrationService).checkRegistered(LECTURE_ID, STUDENT_ID);
        verify(lectureService).checkStatus(LECTURE_ID);
        verify(registrationService, never()).register(anyLong(), anyString());
        verify(lectureService, never()).incrementCapacity(any());
    }

    @Test
    @DisplayName("특정 특강의 신청 수용 인원이 다 차있지 않고 중복 신청이 아니라면 성공한다.")
    void registerLecture() {
        // given
        doNothing().when(registrationService).checkRegistered(LECTURE_ID, STUDENT_ID);
        when(lectureService.checkStatus(LECTURE_ID)).thenReturn(lecture);
        doNothing().when(registrationService).register(LECTURE_ID, STUDENT_ID);
        doNothing().when(lectureService).incrementCapacity(lecture);

        // when - then
        assertThatCode(() -> lectureFacade.register(request)).doesNotThrowAnyException();

        verify(registrationService).checkRegistered(LECTURE_ID, STUDENT_ID);
        verify(lectureService).checkStatus(LECTURE_ID);
        verify(registrationService).register(LECTURE_ID, STUDENT_ID);
        verify(lectureService).incrementCapacity(lecture);
    }

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