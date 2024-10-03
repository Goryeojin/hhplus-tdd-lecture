package io.hhplus.lecture.domain.service;

import io.hhplus.lecture.support.exception.CustomException;
import io.hhplus.lecture.support.exception.ErrorCode;
import io.hhplus.lecture.domain.model.Registration;
import io.hhplus.lecture.domain.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository repository;

    // 특강 신청 등록
    public void register(Long lectureId, String studentId) {
        repository.save(lectureId, studentId);
    }

    // 특강 신청 목록 조회
    public List<Registration> registrations(String studentId) {
        return repository.findByStudentId(studentId);
    }

    // 특강 중복 신청 여부 확인
    public void checkRegistered(Long lectureId, String studentId) {
        if (repository.checkRegistered(lectureId, studentId)) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED);
        }
    }
}
