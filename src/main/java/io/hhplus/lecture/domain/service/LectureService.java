package io.hhplus.lecture.domain.service;

import io.hhplus.lecture.domain.model.Lecture;
import io.hhplus.lecture.domain.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository repository;

    // 날짜별 신청 가능한 특강을 가져온다.
    public List<Lecture> lecturesAvailable(LocalDate lectureDate) {
        return repository.findAllAvailableByDate(lectureDate);
    }

    // 특정 특강을 조회하고 수용 인원을 확인한다.
    public Lecture checkStatus(Long lectureId) {
        // 특정 특강 조회
        Lecture lecture = repository.findById(lectureId);
        // 신청일이 특강일 이전인지 확인
        lecture.checkLectureDate();
        // 수용 인원 확인
        lecture.checkCapacity();
        return lecture;
    }

    // 특정 특강의 상태를 변경한다.
    public void incrementCapacity(Lecture lecture) {
        Lecture updateLecture = lecture.incrementCapacity();
        repository.incrementCapacity(updateLecture);
    }
}
