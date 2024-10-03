package io.hhplus.lecture.api.web;

import io.hhplus.lecture.application.dto.LectureResponse;
import io.hhplus.lecture.application.dto.RegisterRequest;
import io.hhplus.lecture.application.facade.LectureFacade;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureFacade lectureFacade;

    /** 특강 신청 API
     *
     * @param request 신청 요청 DTO (lectureId, studentId)
     * @return CREATED
     */
    @PostMapping("/register")
    public HttpStatus register(@RequestBody @Validated RegisterRequest request) {
        lectureFacade.register(request);
        return HttpStatus.CREATED;
    }

    /** 특강 선택 API
     * @return 날짜별 신청 가능한 특강 목록
     */
    @GetMapping
    public ResponseEntity<LectureResponse> lecturesAvailable(@RequestParam LocalDate date) {
        return ResponseEntity.ok(lectureFacade.lecturesAvailable(date));
    }

    /** 특강 신청 완료 목록 조회 API
     *
     * @param studentId 학생 ID
     * @return 신청 완료된 특강 목록
     */
    @GetMapping("/{studentId}")
    public ResponseEntity<LectureResponse> registrations(@PathVariable @NotBlank String studentId) {
        return ResponseEntity.ok(lectureFacade.registrations(studentId));
    }
}
