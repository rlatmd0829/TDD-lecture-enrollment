package com.example.lecture.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lecture.domain.entity.Enrollment;
import com.example.lecture.domain.entity.Lecture;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	boolean existsByLecture_IdAndUserId(Long lectureId, Long userId);
}
