package com.example.lecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lecture.domain.entity.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	boolean existsByLecture_IdAndUserId(Long lectureId, Long userId);
}
