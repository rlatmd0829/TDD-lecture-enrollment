package com.example.lecture.domain.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.lecture.domain.enumclass.LectureStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lectures")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Lecture {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 30, nullable = false)
	private String name;

	@Column(name = "lecture_status", length = 30, nullable = false)
	@Enumerated(EnumType.STRING)
	private LectureStatus lectureStatus;

	@OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Enrollment> enrollments = new ArrayList<>();

	public boolean isEnrollmentLimitExceeded(int maxEnrollmentLimit) {
		return enrollments.size() >= maxEnrollmentLimit;
	}
}
