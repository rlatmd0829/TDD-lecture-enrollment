package com.example.lecture.integration;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.lecture.domain.entity.Lecture;
import com.example.lecture.application.request.EnrollmentRequest;
import com.example.lecture.domain.repository.LectureRepository;
import com.example.lecture.application.EnrollmentServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class EnrollmentServiceTest {

	@Autowired
	private EnrollmentServiceImpl enrollmentService;
	@Autowired
	private LectureRepository lectureRepository;

	@BeforeEach
	void setUp() {
		lectureRepository.save(new Lecture(1L, "특강1", 30, LocalDateTime.now(), new ArrayList<>()));
	}

	@Test
	@DisplayName("동시에 특강 신청 테스트")
	void testConcurrentEnrollments() throws InterruptedException {
		Long lectureId = 1L;
		int numberOfEnrollments = 31;

		ExecutorService executorService = Executors.newFixedThreadPool(numberOfEnrollments);

		for (long i = 0; i < numberOfEnrollments; i++) {
			Long userId = i;
			executorService.execute(() -> {
				EnrollmentRequest enrollmentRequest = new EnrollmentRequest(userId);
				enrollmentService.enroll(lectureId, enrollmentRequest);
			});
		}

		executorService.shutdown();
		executorService.awaitTermination(10, TimeUnit.SECONDS);

		Optional<Lecture> lectureOptional = lectureRepository.findById(lectureId);
		Lecture lecture = lectureOptional.get();

		assertThat(lecture.getEnrollments().size()).isEqualTo(30);
	}

	@Test
	@DisplayName("한명의 유저가 동시에 특강 신청 테스트")
	void testConcurrentEnrollmentBySingleUser() throws InterruptedException {
		Long lectureId = 1L;
		int numberOfEnrollments = 31;

		ExecutorService executorService = Executors.newFixedThreadPool(numberOfEnrollments);

		for (long i = 0; i < numberOfEnrollments; i++) {
			executorService.execute(() -> {
				EnrollmentRequest enrollmentRequest = new EnrollmentRequest(1L);
				enrollmentService.enroll(lectureId, enrollmentRequest);
			});
		}

		executorService.shutdown();
		executorService.awaitTermination(10, TimeUnit.SECONDS);

		Optional<Lecture> lectureOptional = lectureRepository.findById(lectureId);
		Lecture lecture = lectureOptional.get();

		assertThat(lecture.getEnrollments().size()).isEqualTo(1);
	}
}
