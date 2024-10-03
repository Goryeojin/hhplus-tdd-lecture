package io.hhplus.lecture.infra.repository;

import io.hhplus.lecture.infra.entity.LectureCapacityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectureCapacityJpaRepository extends JpaRepository<LectureCapacityEntity, Long> {
    Optional<LectureCapacityEntity> findByLectureId(Long lectureId);
}
