package com.example.lecture.application;

import java.util.List;

import com.example.lecture.application.response.LectureResponse;

public interface LectureService {
	List<LectureResponse> getLectures();
}
