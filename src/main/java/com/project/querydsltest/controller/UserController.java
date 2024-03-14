package com.project.querydsltest.controller;

import com.project.querydsltest.dto.SignUpRequestDto;
import com.project.querydsltest.dto.UserResponseDto;
import com.project.querydsltest.dto.user.UsersResponseDto;
import com.project.querydsltest.global.response.CommonResponse;
import com.project.querydsltest.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<CommonResponse<UserResponseDto>> createUser(
        @RequestBody SignUpRequestDto req
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(
          CommonResponse.<UserResponseDto>builder()
              .message("회원가입이 성공하였습니다.")
              .data(userService.createUser(req))
              .build()
        );
    }

    @GetMapping("/users/id")
    public ResponseEntity<CommonResponse<UserResponseDto>> getUser(){
        return ResponseEntity.status(HttpStatus.OK).body(
            CommonResponse.<UserResponseDto>builder()
                .message("회원조회가 성공하였습니다.")
                .data(userService.getUser())
                .build()
        );
    }

    @GetMapping("/users")
    public ResponseEntity<CommonResponse<UsersResponseDto>> getUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(
            CommonResponse.<UsersResponseDto>builder()
                .message("회원조회가 성공하였습니다.")
                .data(userService.getUsers())
                .build()
        );
    }

}
