package io.hhplus.lecture.domain.service;

import io.hhplus.lecture.api.common.exception.CustomException;
import io.hhplus.lecture.api.common.exception.ErrorCode;
import io.hhplus.lecture.domain.model.Registration;
import io.hhplus.lecture.domain.repository.RegistrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private RegistrationService registrationService;

    private static Long LECTURE_ID = 1L;
    private static String STUDENT_ID = "test";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 정상적으로_특강_신청을_완료한다() {
        // given
        doNothing().when(registrationRepository).save(LECTURE_ID, STUDENT_ID);

        // when
        assertDoesNotThrow(() -> registrationService.register(LECTURE_ID, STUDENT_ID));

        // then
        verify(registrationRepository).save(LECTURE_ID, STUDENT_ID);
    }

    @Test
    void 중복_신청일_경우_예외_발생() {
        // given
        when(registrationRepository.checkRegistered(LECTURE_ID, STUDENT_ID)).thenReturn(true);

        // when
        CustomException exception = assertThrows(CustomException.class, () -> registrationService.checkRegistered(LECTURE_ID, STUDENT_ID));

        // then
        assertEquals(ErrorCode.ALREADY_REGISTERED, exception.getErrorCode());
        verify(registrationRepository).checkRegistered(LECTURE_ID, STUDENT_ID);
    }

    @Test
    void 중복_신청이_아닌_경우_예외_발생하지_않음() {
        // given
        when(registrationRepository.checkRegistered(LECTURE_ID, STUDENT_ID)).thenReturn(false);

        // when
        assertDoesNotThrow(() -> registrationService.checkRegistered(LECTURE_ID, STUDENT_ID));

        // then
        verify(registrationRepository).checkRegistered(LECTURE_ID, STUDENT_ID);
    }
}