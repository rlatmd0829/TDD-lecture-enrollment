package com.example.lecture.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.lecture.domain.entity.Enrollment;
import com.example.lecture.domain.entity.Lecture;
import com.example.lecture.application.request.EnrollmentRequest;
import com.example.lecture.application.exception.CustomException;
import com.example.lecture.application.exception.ErrorCode;
import com.example.lecture.domain.repository.EnrollmentRepository;
import com.example.lecture.domain.repository.LectureRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

	private final LectureRepository lectureRepository;
	private final EnrollmentRepository enrollmentRepository;

	@Override
	public void enroll(Long lectureId, EnrollmentRequest enrollmentRequest) {
		Lecture lecture = lectureRepository.findByIdWithLock(lectureId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_LECTURE));

		if (lecture.isFull()) {
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
