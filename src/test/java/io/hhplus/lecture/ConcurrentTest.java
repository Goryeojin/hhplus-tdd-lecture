package io.hhplus.lecture;

import io.hhplus.lecture.application.dto.RegisterRequest;
import io.hhplus.lecture.application.facade.LectureFacade;
import io.hhplus.lecture.domain.model.Lecture;
import io.hhplus.lecture.domain.repository.LectureRepository;
import io.hhplus.lecture.domain.repository.RegistrationRepository;
import io.hhplus.lecture.domain.service.LectureService;
import io.hhplus.lecture.domain.service.RegistrationService;
import io.hhplus.lecture.infra.entity.RegistrationEntity;
import io.hhplus.lecture.infra.repository.LectureCapacityJpaRepository;
import io.hhplus.lecture.infra.repository.LectureJpaRepository;
import io.hhplus.lecture.infra.repository.RegistrationJpaRepository;
import io.hhplus.lecture.support.exception.CustomException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        /*
        40개의 스레드가 개별적으로 등록 요청을 처리하고, 각 스레드가 완료되면 latch.countDown()이 호출되게 한다.
        마지막 스레드가 완료되면 latch.await() 가 해제된다.
        스레드의 순서를 고려하지 않고, 각 스레드가 개별적으로 작업을 수행한 후 결과를 확인하고자 채택함.
         */
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

    @Test
    void 동일한_학생이_같은_특강을_동시에_5번_신청할_때_1번만_성공해야한다() throws InterruptedException {
        String studentId = "student1";

        // when
        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 1; i <= threadCount; i++) {
            executorService.submit(() -> {
                try {
                    RegisterRequest request = new RegisterRequest(1L, studentId);
                    lectureFacade.register(request);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await(); // 모든 스레드가 끝날 때까지 대기

        // 등록된 수 확인
        List<RegistrationEntity> registrationCount = registrationJpaRepository.findByStudentId(studentId);
        assertThat(registrationCount).hasSize(1); // 성공한 등록 수는 1이어야 함

        // 추가적으로 예외 발생 여부 확인
        assertThrows(CustomException.class, () -> {
            lectureFacade.register(new RegisterRequest(1L, studentId));
        });
    }

}