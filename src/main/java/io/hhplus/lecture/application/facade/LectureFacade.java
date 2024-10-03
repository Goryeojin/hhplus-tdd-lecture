package io.hhplus.lecture.application.facade;

import io.hhplus.lecture.application.dto.LectureResponse;
import io.hhplus.lecture.domain.model.Lecture;
import io.hhplus.lecture.domain.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureFacade {

    private final LectureService lectureService;

    // 날짜별 신청 가능한 특강 목록
    public LectureResponse lecturesAvailable(LocalDate lectureDate) {
        List<Lecture> lectures = lectureService.lecturesAvailable(lectureDate);
        return LectureResponse.builder().lectures(lectures).build();
    }
}
