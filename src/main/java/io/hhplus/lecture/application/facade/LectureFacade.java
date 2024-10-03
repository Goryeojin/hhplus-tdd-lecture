package io.hhplus.lecture.application.facade;

import io.hhplus.lecture.application.dto.LectureResponse;
import io.hhplus.lecture.application.dto.RegisterRequest;
import io.hhplus.lecture.domain.model.Lecture;
import io.hhplus.lecture.domain.model.Registration;
import io.hhplus.lecture.domain.service.LectureService;
import io.hhplus.lecture.domain.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureFacade {

    private final LectureService lectureService;
    private final RegistrationService registrationService;

    @Transactional
    public void register(RegisterRequest request) {
        // 중복 신청 여부 확인
        registrationService.checkRegistered(request.lectureId(), request.studentId());
        // 특강 조회
        Lecture lecture = lectureService.checkStatus(request.lectureId());
        // 학생 등록
        registrationService.register(request.lectureId(), request.studentId());
        // 특강 정원 상태 수정
        lectureService.incrementCapacity(lecture);
    }

    // 날짜별 신청 가능한 특강 목록
    public LectureResponse lecturesAvailable(LocalDate lectureDate) {
        List<Lecture> lectures = lectureService.lecturesAvailable(lectureDate);
        return LectureResponse.builder().lectures(lectures).build();
    }

    // 특강 신청 목록 조회
    public LectureResponse registrations(String studentId) {
        List<Registration> registrations = registrationService.registrations(studentId);
        return LectureResponse.builder().registrations(registrations).build();
    }
}
