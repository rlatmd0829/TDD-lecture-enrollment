package com.example.lecture.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.lecture.domain.Lecture;
import com.example.lecture.infra.LectureJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {
	private final LectureJpaRepository lectureJpaRepository;

	@Override
	public Optional<Lecture> findById(Long id) {
		return lectureJpaRepository.findById(id);
	}

	@Override
	public Optional<Lecture> findByIdWithLock(Long id) {
		return lectureJpaRepository.findByIdWithLock(id);
	}

	@Override
	public List<Lecture> findAllByStartDateAfter(LocalDateTime startDate) {
		return lectureJpaRepository.findAllByStartDateAfter(startDate);
	}

	@Override
	public void save(Lecture lecture) {
		lectureJpaRepository.save(lecture);
	}
}
