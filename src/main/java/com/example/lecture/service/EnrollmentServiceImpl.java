package com.example.lecture.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.example.lecture.domain.entity.Enrollment;
import com.example.lecture.domain.entity.Lecture;
import com.example.lecture.dto.request.EnrollmentRequest;
import com.example.lecture.repository.EnrollmentRepository;
import com.example.lecture.repository.LectureRepository;

import jakarta.transaction.Transactional;
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
			.orElseThrow(() -> new NoSuchElementException("강의를 찾을 수 없습니다. lectureId: " + lectureId));

		boolean enrollmentExists = enrollmentRepository.existsByLecture_IdAndUserId(lectureId, enrollmentRequest.userId());
		if (enrollmentExists) {
			throw new RuntimeException("이미 특강 신청을 했습니다.");
		}

		Enrollment enrollment = Enrollment.create(lecture, enrollmentRequest);
		enrollmentRepository.save(enrollment);
	}
}
