package com.example.lecture.application;

import com.example.lecture.application.request.EnrollmentRequest;

public interface EnrollmentService {
	void enroll(Long lectureId, EnrollmentRequest enrollmentRequest);
	void verifyEnrollment(Long lectureId, Long userId);
}
