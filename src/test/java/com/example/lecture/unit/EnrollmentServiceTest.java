package com.example.lecture.unit;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.lecture.domain.entity.Lecture;
import com.example.lecture.domain.enumclass.LectureStatus;
import com.example.lecture.dto.request.EnrollmentRequest;
import com.example.lecture.exception.CustomException;
import com.example.lecture.exception.ErrorCode;
import com.example.lecture.repository.EnrollmentRepository;
import com.example.lecture.repository.LectureRepository;
import com.example.lecture.service.EnrollmentServiceImpl;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {
	@InjectMocks
	private EnrollmentServiceImpl enrollmentService;
	@Mock
	private LectureRepository lectureRepository;
	@Mock
	private EnrollmentRepository enrollmentRepository;

	@Test
	@DisplayName("특강 신청 성공 테스트")
	void enrollLectureTest() {
		// given
		Long lectureId = 1L;
		String name = "특강1";
		LectureStatus lectureStatus = LectureStatus.ACTIVE;
		Long userId = 1L;
		EnrollmentRequest enrollmentRequest = new EnrollmentRequest(userId);

		// when
		when(lectureRepository.findById(anyLong())).thenReturn(
			Optional.of(new Lecture(lectureId, name, lectureStatus, new ArrayList<>())));
		when(enrollmentRepository.existsByLecture_IdAndUserId(anyLong(), anyLong())).thenReturn(false);
		enrollmentService.enroll(lectureId, enrollmentRequest);

		// then
		verify(lectureRepository, times(1)).findById(anyLong());
		verify(enrollmentRepository, times(1)).existsByLecture_IdAndUserId(anyLong(), anyLong());
		verify(enrollmentRepository, times(1)).save(any());
	}

	@Test
	@DisplayName("특강을 찾지 못했을 경우 에러 반환 테스트")
	void enrollLectureTest_whenLectureNotFound_thenThrowException() {
		// given
		Long lectureId = 1L;
		Long userId = 1L;
		EnrollmentRequest enrollmentRequest = new EnrollmentRequest(userId);

		// when
		when(lectureRepository.findById(anyLong())).thenReturn(Optional.empty());

		// then
		assertThatThrownBy(() -> enrollmentService.enroll(lectureId, enrollmentRequest))
			.isInstanceOf(CustomException.class)
			.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_FOUND_LECTURE);
	}

	@Test
	@DisplayName("특정 유저가 해당 특강에 이미 신청을 한 상태 일경우 에러 반환 테스트")
	void enrollLectureTest_whenAlreadyEnrolled_thenThrowException() {
		// given
		Long lectureId = 1L;
		String name = "특강1";
		LectureStatus lectureStatus = LectureStatus.ACTIVE;
		Long userId = 1L;
		EnrollmentRequest enrollmentRequest = new EnrollmentRequest(userId);

		// when
		when(lectureRepository.findById(anyLong())).thenReturn(
			Optional.of(new Lecture(lectureId, name, lectureStatus, new ArrayList<>())));
		when(enrollmentRepository.existsByLecture_IdAndUserId(anyLong(), anyLong())).thenReturn(true);

		// then
		assertThatThrownBy(() -> enrollmentService.enroll(lectureId, enrollmentRequest))
			.isInstanceOf(CustomException.class)
			.hasFieldOrPropertyWithValue("errorCode", ErrorCode.DUPLICATE_ENROLLMENT);
	}

	@Test
	@DisplayName("특강 신청 여부 확인 테스트")
	void verifyEnrollmentTest() {
		// given
		Long lectureId = 1L;
		String name = "특강1";
		LectureStatus lectureStatus = LectureStatus.ACTIVE;
		Long userId = 1L;

		// when
		when(lectureRepository.findById(anyLong())).thenReturn(
			Optional.of(new Lecture(lectureId, name, lectureStatus, new ArrayList<>())));
		when(enrollmentRepository.existsByLecture_IdAndUserId(anyLong(), anyLong())).thenReturn(true);
		enrollmentService.verifyEnrollment(lectureId, userId);

		// then
		verify(lectureRepository, times(1)).findById(anyLong());
		verify(enrollmentRepository, times(1)).existsByLecture_IdAndUserId(anyLong(), anyLong());
	}

	@Test
	@DisplayName("특강 신청 안 했을 경우 에러 반환 테스트")
	void verifyEnrollmentTest_whenNotEnrolled_thenThrowException() {
		// given
		Long lectureId = 1L;
		String name = "특강1";
		LectureStatus lectureStatus = LectureStatus.ACTIVE;
		Long userId = 1L;

		// when
		when(lectureRepository.findById(anyLong())).thenReturn(
			Optional.of(new Lecture(lectureId, name, lectureStatus, new ArrayList<>())));
		when(enrollmentRepository.existsByLecture_IdAndUserId(anyLong(), anyLong())).thenReturn(false);

		// then
		assertThatThrownBy(() -> enrollmentService.verifyEnrollment(lectureId, userId))
			.isInstanceOf(CustomException.class)
			.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_FOUND_ENROLLMENT);
	}
}
