package com.project.querydsltest.controller;

import com.project.querydsltest.dto.SignUpRequestDto;
import com.project.querydsltest.dto.UserResponseDto;
import com.project.querydsltest.dto.user.UserPageResponseDto;
import com.project.querydsltest.dto.user.UsersResponseDto;
import com.project.querydsltest.global.response.CommonResponse;
import com.project.querydsltest.service.user.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
        @Valid @RequestBody SignUpRequestDto req
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

    @GetMapping("/users/page")
    public ResponseEntity<CommonResponse<UserPageResponseDto>> getUsersByPage(
        @PageableDefault(page = 1)Pageable pageable
    ){
        return ResponseEntity.status(HttpStatus.OK).body(
            CommonResponse.<UserPageResponseDto>builder()
                .message("page 테스트 성공")
                .data(userService.getUserByPage(pageable))
                .build()
        );
    }

}
