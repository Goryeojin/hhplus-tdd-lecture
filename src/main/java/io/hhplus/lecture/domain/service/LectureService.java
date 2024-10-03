package io.hhplus.lecture.domain.service;

import io.hhplus.lecture.domain.model.Lecture;
import io.hhplus.lecture.domain.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository repository;

    // 날짜별 신청 가능한 특강을 가져온다.
    public List<Lecture> lecturesAvailable(LocalDate lectureDate) {
        return repository.findAllAvailableByDate(lectureDate);
    }
}
