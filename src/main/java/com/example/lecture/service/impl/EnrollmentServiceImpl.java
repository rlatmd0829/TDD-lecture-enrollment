package com.example.lecture.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.lecture.domain.entity.Enrollment;
import com.example.lecture.domain.entity.Lecture;
import com.example.lecture.dto.request.EnrollmentRequest;
import com.example.lecture.exception.CustomException;
import com.example.lecture.exception.ErrorCode;
import com.example.lecture.repository.EnrollmentRepository;
import com.example.lecture.repository.LectureRepository;
import com.example.lecture.service.EnrollmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

	private final LectureRepository lectureRepository;
	private final EnrollmentRepository enrollmentRepository;
	private static final int MAX_ENROLLMENT_LIMIT = 30;

	@Override
	public void enroll(Long lectureId, EnrollmentRequest enrollmentRequest) {
		Lecture lecture = lectureRepository.findByIdWithLock(lectureId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_LECTURE));

		if (enrollmentRepository.countByLecture_Id(lectureId) >= MAX_ENROLLMENT_LIMIT) {
			throw new CustomException(ErrorCode.ENROLLMENT_LIMIT_EXCEEDED);
		}

		boolean enrollmentExists = enrollmentRepository.existsByLecture_IdAndUserId(lecture.getId(), enrollmentRequest.userId());
		if (enrollmentExists) {
			throw new CustomException(ErrorCode.DUPLICATE_ENROLLMENT);
		}

		Enrollment enrollment = Enrollment.builder()
			.lecture(lecture)
			.userId(enrollmentRequest.userId())
			.build();
		enrollmentRepository.save(enrollment);

	}

	@Override
	@Transactional(readOnly = true)
	public void verifyEnrollment(Long lectureId, Long userId) {
		Lecture lecture = lectureRepository.findById(lectureId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_LECTURE));

		boolean enrollmentExists = enrollmentRepository.existsByLecture_IdAndUserId(lecture.getId(), userId);
		if (!enrollmentExists) {
			throw new CustomException(ErrorCode.NOT_FOUND_ENROLLMENT);
		}
	}
}
