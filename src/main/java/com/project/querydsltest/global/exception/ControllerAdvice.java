package com.project.querydsltest.global.exception;

import com.project.querydsltest.global.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j(topic = "Exception")
@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<CommonResponse<String>> handleValidationException(
        NullPointerException e) {
        log.error("유저가 존재하지 않습니다.", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            CommonResponse.<String>builder()
                .message(e.getMessage())
                .build()
        );
    }
}
