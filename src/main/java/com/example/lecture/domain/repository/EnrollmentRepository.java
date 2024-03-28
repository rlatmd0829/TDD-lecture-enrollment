package com.example.lecture.domain.repository;

import com.example.lecture.domain.Enrollment;


public interface EnrollmentRepository {

	boolean existsByLecture_IdAndUserId(Long lectureId, Long userId);

	int countByLecture_Id(Long lectureId);

	void save(Enrollment enrollment);
}
