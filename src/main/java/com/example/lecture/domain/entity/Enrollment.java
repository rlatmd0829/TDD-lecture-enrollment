package com.example.lecture.domain.entity;

import com.example.lecture.application.request.EnrollmentRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(
	name = "enrollments",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"user_id", "lecture_id"})
	}
)
// @Table(name = "enrollments")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lecture_id")
	private Lecture lecture;

	public static Enrollment create(Lecture lecture, EnrollmentRequest enrollmentRequest) {
		return Enrollment.builder()
			.userId(enrollmentRequest.userId())
			.lecture(lecture)
			.build();
	}
}
