package com.example.lecture.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lecture.domain.entity.Enrollment;

public interface EnrollmentJpaRepository extends JpaRepository<Enrollment, Long> {
	boolean existsByLecture_IdAndUserId(Long lectureId, Long userId);
}
