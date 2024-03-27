package com.example.lecture.service;

import java.util.List;

import com.example.lecture.dto.response.LectureResponse;

public interface LectureService {
	List<LectureResponse> lectures();
}
