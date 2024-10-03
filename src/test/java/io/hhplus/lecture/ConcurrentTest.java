package io.hhplus.lecture;

import io.hhplus.lecture.application.dto.RegisterRequest;
import io.hhplus.lecture.application.facade.LectureFacade;
import io.hhplus.lecture.domain.model.Lecture;
import io.hhplus.lecture.domain.repository.LectureRepository;
import io.hhplus.lecture.domain.repository.RegistrationRepository;
import io.hhplus.lecture.domain.service.LectureService;
import io.hhplus.lecture.domain.service.RegistrationService;
import io.hhplus.lecture.infra.repository.LectureCapacityJpaRepository;
import io.hhplus.lecture.infra.repository.LectureJpaRepository;
import io.hhplus.lecture.infra.repository.RegistrationJpaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ConcurrentTest {
    @Autowired
    private LectureFacade lectureFacade;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private LectureCapacityJpaRepository lectureCapacityJpaRepository;

    @Autowired
    private LectureJpaRepository lectureJpaRepository;

    @Autowired
    private RegistrationJpaRepository registrationJpaRepository;

    @Test
    void 동시에_40명이_특강을_신청할_때_정확히_30명만_성공해야_한다() throws InterruptedException {
        // given
        Long lectureId = 1L; // 테스트할 특강 ID
        String studentIdPrefix = "student-";

        // when
        int threadCount = 40;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 1; i <= threadCount; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    String studentId = studentIdPrefix + finalI;
                    RegisterRequest request = new RegisterRequest(lectureId, studentId);
                    lectureFacade.register(request);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 스레드가 끝날 때까지 대기

        // then
        // 특강 정원이 정확히 30명인지 확인
        Lecture updatedLecture = lectureRepository.findById(lectureId);
        System.out.println(updatedLecture);
        assertThat(updatedLecture.currentCapacity()).isEqualTo(30);
    }
}