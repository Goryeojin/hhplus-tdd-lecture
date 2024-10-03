package io.hhplus.lecture.infra.repository;

import io.hhplus.lecture.infra.entity.RegistrationEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface RegistrationJpaRepository extends JpaRepository<RegistrationEntity, Long> {
    List<RegistrationEntity> findByStudentId(String studentId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<RegistrationEntity> findByLectureIdAndStudentId(Long lectureId, String studentId);
}
