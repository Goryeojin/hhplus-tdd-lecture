package io.hhplus.lecture.domain.repository;

import io.hhplus.lecture.domain.model.Lecture;
import io.hhplus.lecture.domain.model.LectureCapacity;
import io.hhplus.lecture.domain.model.Registration;

import java.time.LocalDate;
import java.util.List;

public interface LectureRepository {
    Lecture findById(Long lectureId);
    void incrementCapacity(Lecture lecture);

    List<Lecture> findAllAvailableByDate(LocalDate lectureDate);

    List<LectureCapacity> capacities();
}
