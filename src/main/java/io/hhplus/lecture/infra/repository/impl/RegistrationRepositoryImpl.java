package io.hhplus.lecture.infra.repository.impl;

import io.hhplus.lecture.domain.model.Registration;
import io.hhplus.lecture.domain.repository.RegistrationRepository;
import io.hhplus.lecture.infra.entity.RegistrationEntity;
import io.hhplus.lecture.infra.repository.RegistrationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RegistrationRepositoryImpl implements RegistrationRepository {

    private final RegistrationJpaRepository registrationJpaRepository;

    // 특강 신청 등록
    @Override
    public void save(Long lectureId, String studentId) {
        RegistrationEntity registration = RegistrationEntity.of(lectureId, studentId);
        registrationJpaRepository.save(registration);
    }

    @Override
    public List<Registration> findByStudentId(String studentId) {
        return null;
    }

    // 동일한 아이디로 신청했는지 확인
    @Override
    public boolean checkRegistered(Long lectureId, String studentId) {
        return registrationJpaRepository.findByLectureIdAndStudentId(lectureId, studentId).isPresent();
    }
}
