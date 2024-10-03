package io.hhplus.lecture.domain.repository;

import io.hhplus.lecture.domain.model.Registration;

import java.util.List;

public interface RegistrationRepository {
    void save(Long lectureId, String studentId);

    List<Registration> findByStudentId(String studentId);

    boolean checkRegistered(Long lectureId, String studentId);
}
