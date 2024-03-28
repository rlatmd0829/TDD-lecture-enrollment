package com.example.lecture.domain.repository;

import org.springframework.stereotype.Repository;

import com.example.lecture.domain.entity.Enrollment;
import com.example.lecture.infra.EnrollmentJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EnrollmentRepositoryImpl implements EnrollmentRepository {
	private final EnrollmentJpaRepository enrollmentJpaRepository;

	@Override
	public boolean existsByLecture_IdAndUserId(Long lectureId, Long userId) {
		return enrollmentJpaRepository.existsByLecture_IdAndUserId(lectureId, userId);
	}

	@Override
	public int countByLecture_Id(Long lectureId) {
		return enrollmentJpaRepository.countByLecture_Id(lectureId);
	}

	@Override
	public void save(Enrollment enrollment) {
		enrollmentJpaRepository.save(enrollment);
	}
}
