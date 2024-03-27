package com.example.lecture.dto.response;

import java.time.LocalDateTime;

import com.example.lecture.domain.entity.Lecture;

public record LectureResponse(
	Long lectureId,
	String name,
	LocalDateTime startDate
) {
	public static LectureResponse of(Lecture lecture) {
		return new LectureResponse(
			lecture.getId(),
			lecture.getName(),
			lecture.getStartDate()
		);
	}
}
