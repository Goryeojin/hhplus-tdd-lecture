package io.hhplus.lecture.infra.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity(name = "lecture_capacity")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureCapacityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    @Column(nullable = false)
    @ColumnDefault("30")
    private int capacity;

    @Column(name = "current_count", nullable = false)
    @ColumnDefault("0")
    private int currentCount;
}
