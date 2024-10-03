package io.hhplus.lecture.infra.repository;

import io.hhplus.lecture.infra.entity.LectureEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureJpaRepository extends JpaRepository<LectureEntity, Long> {
    @Query("SELECT l FROM lecture l JOIN l.lectureCapacity c " +
            "WHERE l.lectureDateTime >= :startDateTime " +
            "AND l.lectureDateTime < :endDateTime " +
            "AND c.currentCount < c.capacity")
    List<LectureEntity> findAvailableLectures(@Param("startDateTime") LocalDateTime startDateTime,
                                              @Param("endDateTime") LocalDateTime endDateTime);
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<LectureEntity> findById(Long lectureId);
}
