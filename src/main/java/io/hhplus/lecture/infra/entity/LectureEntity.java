package io.hhplus.lecture.infra.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "lecture")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lecture_title", nullable = false)
    private String lectureTitle;

    @Column(nullable = false)
    private String lecturer;

    @Column(name = "lecture_date_time", nullable = false)
    private LocalDateTime lectureDateTime;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "lectureId")
    private LectureCapacityEntity lectureCapacity;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RegistrationEntity> registration;
}
