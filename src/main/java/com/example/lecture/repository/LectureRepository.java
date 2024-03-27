package com.example.lecture.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.lecture.domain.entity.Lecture;

import jakarta.persistence.LockModeType;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

	@Query("SELECT l FROM Lecture l WHERE l.id = :id")
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Lecture> findByIdWithLock(@Param("id") Long id);

	List<Lecture> findAllByStartDateAfter(LocalDateTime startDate);
}
