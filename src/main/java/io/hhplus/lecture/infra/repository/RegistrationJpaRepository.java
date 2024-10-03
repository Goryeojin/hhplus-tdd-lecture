package io.hhplus.lecture.infra.repository;

import io.hhplus.lecture.infra.entity.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationJpaRepository extends JpaRepository<RegistrationEntity, Long> {
    List<RegistrationEntity> findByStudentId(String studentId);
    Optional<RegistrationEntity> findByLectureIdAndStudentId(Long lectureId, String studentId);
}
