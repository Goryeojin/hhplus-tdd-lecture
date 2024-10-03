package io.hhplus.lecture.infra.repository.impl;

import io.hhplus.lecture.api.common.exception.CustomException;
import io.hhplus.lecture.api.common.exception.ErrorCode;
import io.hhplus.lecture.domain.model.Lecture;
import io.hhplus.lecture.domain.repository.LectureRepository;
import io.hhplus.lecture.infra.entity.LectureCapacityEntity;
import io.hhplus.lecture.infra.entity.LectureEntity;
import io.hhplus.lecture.infra.repository.LectureCapacityJpaRepository;
import io.hhplus.lecture.infra.repository.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;
    private final LectureCapacityJpaRepository capacityJpaRepository;

    // 특정 특강 조회
    @Override
    public Lecture findById(Long lectureId) {
        LectureEntity lecture = lectureJpaRepository.findById(lectureId)
                .orElseThrow(() -> new CustomException(ErrorCode.LECTURE_NOT_FOUND));
        LectureCapacityEntity capacity = lecture.getLectureCapacity();
        if (capacity == null) {
            throw new CustomException(ErrorCode.LECTURE_CAPACITY_NOT_FOUND);
        }
        return Lecture.builder()
                .lectureId(lecture.getId())
                .lectureTitle(lecture.getLectureTitle())
                .lecturer(lecture.getLecturer())
                .lectureDateTime(lecture.getLectureDateTime())
                .capacity(capacity.getCapacity())
                .currentCapacity(capacity.getCurrentCount())
                .build();
    }

    // 특정 특강의 현재 정원 수 업데이트
    @Override
    @Transactional
    public void incrementCapacity(Lecture lecture) {
        LectureCapacityEntity capacity = capacityJpaRepository.findByLectureId(lecture.lectureId())
                .orElseThrow(() -> new CustomException(ErrorCode.LECTURE_CAPACITY_NOT_FOUND));
        capacity.setCurrentCount(lecture.currentCapacity());
        capacityJpaRepository.save(capacity);
    }

    // 정원이 남아있는 특강 목록 조회
    @Override
    public List<Lecture> findAllAvailableByDate(LocalDate lectureDate) {
        LocalDateTime startOfDay = lectureDate.atStartOfDay(); // 해당 날짜의 시작 시간
        LocalDateTime endOfDay = lectureDate.atTime(23, 59, 59); // 해당 날짜의 종료 시간

        return lectureJpaRepository.findAvailableLectures(startOfDay, endOfDay).stream()
                .map(entity -> Lecture.builder()
                        .lectureId(entity.getId())
                        .lectureTitle(entity.getLectureTitle())
                        .lecturer(entity.getLecturer())
                        .lectureDateTime(entity.getLectureDateTime())
                        .capacity(entity.getLectureCapacity().getCapacity())
                        .currentCapacity(entity.getLectureCapacity().getCurrentCount())
                        .build())
                .toList();
    }
}
