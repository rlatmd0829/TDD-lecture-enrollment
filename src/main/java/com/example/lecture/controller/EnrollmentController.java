package com.example.lecture.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lecture.dto.request.EnrollmentRequest;
import com.example.lecture.service.EnrollmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class EnrollmentController {

	private final EnrollmentService enrollmentService;

	@PostMapping("/{lectureId}/enrollment")
	public ResponseEntity<Void> enroll(
		@PathVariable long lectureId,
		@Valid @RequestBody EnrollmentRequest enrollmentRequest
	) {
		enrollmentService.enroll(lectureId, enrollmentRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
