package com.example.lecture.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.lecture.domain.entity.Lecture;
import com.example.lecture.domain.repository.LectureRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class LectureControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private LectureRepository lectureRepository;

	@BeforeEach
	void setUp() {
		Lecture lecture1 = new Lecture(1L, "특강1", LocalDateTime.now().plusDays(1), new ArrayList<>());
		Lecture lecture2 = new Lecture(2L, "특강2", LocalDateTime.now().minusDays(1), new ArrayList<>());
		Lecture lecture3 = new Lecture(3L, "특강3", LocalDateTime.now().plusDays(10), new ArrayList<>());
		Lecture lecture4 = new Lecture(4L, "특강3", LocalDateTime.now(), new ArrayList<>());
		lectureRepository.save(lecture1);
		lectureRepository.save(lecture2);
		lectureRepository.save(lecture3);
		lectureRepository.save(lecture4);
	}

	@Test
	@DisplayName("현재 시간 이후 특강 목록 조회 테스트")
	void testLectures() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/lectures"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(2));
	}
}
