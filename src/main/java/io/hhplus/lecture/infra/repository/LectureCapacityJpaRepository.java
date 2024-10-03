package io.hhplus.lecture.infra.repository;

import io.hhplus.lecture.infra.entity.LectureCapacityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LectureCapacityJpaRepository extends JpaRepository<LectureCapacityEntity, Long> {
    Optional<LectureCapacityEntity> findByLectureId(Long lectureId);
    @Query("SELECT lc FROM lecture_capacity lc WHERE lc.currentCount < lc.capacity")
    List<LectureCapacityEntity> findByCurrentCountLessThanCapacity();
}
