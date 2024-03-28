package com.example.lecture.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import com.example.lecture.domain.Lecture;


public interface LectureRepository {
	Optional<Lecture> findById(Long id);
	Optional<Lecture> findByIdWithLock(Long id);
	List<Lecture> findAllByStartDateAfter(LocalDateTime startDate);
	void save(Lecture lecture);
}
