package io.hhplus.lecture.api.web;

import io.hhplus.lecture.application.dto.LectureResponse;
import io.hhplus.lecture.application.facade.LectureFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureFacade lectureFacade;

    /** 특강 선택 API
     * @return 날짜별 신청 가능한 특강 목록
     */
    @GetMapping
    public ResponseEntity<LectureResponse> lecturesAvailable(@RequestParam LocalDate date) {
        return ResponseEntity.ok(lectureFacade.lecturesAvailable(date));
    }
}
