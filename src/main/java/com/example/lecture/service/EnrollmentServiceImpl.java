package com.example.lecture.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.lecture.domain.entity.Enrollment;
import com.example.lecture.domain.entity.Lecture;
import com.example.lecture.dto.request.EnrollmentRequest;
import com.example.lecture.exception.CustomException;
import com.example.lecture.exception.ErrorCode;
import com.example.lecture.repository.EnrollmentRepository;
import com.example.lecture.repository.LectureRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

	private final LectureRepository lectureRepository;
	private final EnrollmentRepository enrollmentRepository;

	@Override
	public void enroll(Long lectureId, EnrollmentRequest enrollmentRequest) {
		Lecture lecture = lectureRepository.findById(lectureId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_LECTURE));

		boolean enrollmentExists = enrollmentRepository.existsByLecture_IdAndUserId(lectureId, enrollmentRequest.userId());
		if (enrollmentExists) {
			throw new CustomException(ErrorCode.DUPLICATE_ENROLLMENT);
		}

		Enrollment enrollment = Enrollment.create(lecture, enrollmentRequest);
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
