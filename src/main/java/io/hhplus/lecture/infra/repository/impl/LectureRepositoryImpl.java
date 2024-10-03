package io.hhplus.lecture.infra.repository.impl;

import io.hhplus.lecture.domain.model.Lecture;
import io.hhplus.lecture.domain.model.LectureCapacity;
import io.hhplus.lecture.domain.repository.LectureRepository;
import io.hhplus.lecture.infra.repository.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public Lecture findById(Long lectureId) {
        return null;
    }

    @Override
    public void incrementCapacity(Lecture lecture) {

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

    @Override
    public List<LectureCapacity> capacities() {
        return null;
    }

}
