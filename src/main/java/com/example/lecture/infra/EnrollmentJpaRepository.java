package com.example.lecture.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lecture.domain.Enrollment;

public interface EnrollmentJpaRepository extends JpaRepository<Enrollment, Long> {
	boolean existsByLecture_IdAndUserId(Long lectureId, Long userId);

	int countByLecture_Id(Long lectureId);
}
