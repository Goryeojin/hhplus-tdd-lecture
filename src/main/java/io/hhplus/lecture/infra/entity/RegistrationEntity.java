package io.hhplus.lecture.infra.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "registration", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "lecture_id"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private String studentId;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계 매핑
    @JoinColumn(name = "lecture_id", nullable = false) // lecture_id가 외래키
    private LectureEntity lecture;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    public static RegistrationEntity of(Long lectureId, String studentId) {
        RegistrationEntity registration = new RegistrationEntity();
        registration.studentId = studentId;
        registration.lecture = new LectureEntity(); // LectureEntity를 새로 생성하거나 기존 LectureEntity를 주입받아야 함
        registration.lecture.setId(lectureId); // lectureId를 설정
        registration.registrationDate = LocalDateTime.now(); // 현재 시간으로 설정
        return registration;
    }
}
