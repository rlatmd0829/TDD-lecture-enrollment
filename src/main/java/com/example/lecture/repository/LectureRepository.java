package com.example.lecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lecture.domain.entity.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
