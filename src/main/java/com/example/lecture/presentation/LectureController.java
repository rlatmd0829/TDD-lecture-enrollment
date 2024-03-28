package com.example.lecture.presentation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lecture.application.response.LectureResponse;
import com.example.lecture.application.LectureService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lectures")
public class LectureController {

	private final LectureService lectureService;

	@GetMapping
	public ResponseEntity<List<LectureResponse>> getLectures(
	) {
		return new ResponseEntity<>(lectureService.getLectures(), HttpStatus.OK);
	}
}
