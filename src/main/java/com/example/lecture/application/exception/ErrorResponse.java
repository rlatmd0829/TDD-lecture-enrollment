package com.example.lecture.application.exception;

import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {
	private int status;
	private String error;
	private String customError;
	private String message;
	private String path;

	@Builder
	private ErrorResponse(
		int status, String error, String customError, String message, String path
	) {
		this.status = status;
		this.error = error;
		this.customError = customError;
		this.message = message;
		this.path = path;
	}

	public static ResponseEntity<ErrorResponse> toResponseEntity(
		ErrorCode errorCode, String message, HttpServletRequest request
	) {
		return ResponseEntity
			.status(errorCode.getHttpStatus())
			.body(ErrorResponse.builder()
				.status(errorCode.getHttpStatus().value())
				.error(errorCode.getHttpStatus().name())
				.customError(errorCode.name())
				.message(message)
				.path(request.getRequestURI())
				.build()
			);
	}
}

