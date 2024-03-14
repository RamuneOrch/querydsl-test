package com.project.querydsltest.global.exception;

import com.project.querydsltest.global.response.CommonResponse;
import com.project.querydsltest.global.response.ErrorResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<List<ErrorResponse>>> handleValidationException(
        MethodArgumentNotValidException e) {
        log.error("잘못된 회원가입 요청입니다.", e);
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<ErrorResponse> ErrorResponseList = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            ErrorResponse exceptionResponse = new ErrorResponse(fieldError.getDefaultMessage());
            ErrorResponseList.add(exceptionResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(
            CommonResponse.<List<ErrorResponse>>builder()
                .message("잘못된 회원가입 요청입니다.")
                .data(ErrorResponseList)
                .build()
        );
    }
}
