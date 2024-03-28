package com.example.lecture.domain.repository;

import com.example.lecture.domain.entity.Enrollment;


public interface EnrollmentRepository {

	boolean existsByLecture_IdAndUserId(Long lectureId, Long userId);

	void save(Enrollment enrollment);
}
