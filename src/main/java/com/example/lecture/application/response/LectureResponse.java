package com.example.lecture.application.response;

import java.time.LocalDateTime;

import com.example.lecture.domain.entity.Lecture;

public record LectureResponse(
	Long lectureId,
	String name,
	int capacity,
	LocalDateTime startDate
) {
	public static LectureResponse of(Lecture lecture) {
		return new LectureResponse(
			lecture.getId(),
			lecture.getName(),
			lecture.getCapacity(),
			lecture.getStartDate()
		);
	}
}
