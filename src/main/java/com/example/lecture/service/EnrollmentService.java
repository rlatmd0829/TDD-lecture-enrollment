package com.example.lecture.service;

import com.example.lecture.dto.request.EnrollmentRequest;

public interface EnrollmentService {
	void enroll(Long lectureId, EnrollmentRequest enrollmentRequest);
}
