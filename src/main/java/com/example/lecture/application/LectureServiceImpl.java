package com.example.lecture.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.lecture.application.response.LectureResponse;
import com.example.lecture.domain.repository.LectureRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureServiceImpl implements LectureService {

	private final LectureRepository lectureRepository;

	@Override
	@Transactional(readOnly = true)
	public List<LectureResponse> getLectures() {
		return lectureRepository.findAllByStartDateAfter(LocalDateTime.now()).stream()
			.map(LectureResponse::of)
			.toList();
	}
}
