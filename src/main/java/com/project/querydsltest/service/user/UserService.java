package com.project.querydsltest.service.user;

import com.project.querydsltest.dto.SignUpRequestDto;
import com.project.querydsltest.dto.UserResponseDto;
import com.project.querydsltest.dto.user.UsersResponseDto;
import org.springframework.stereotype.Service;

public interface UserService {
    UserResponseDto createUser(SignUpRequestDto req);

    UserResponseDto getUser();

    UsersResponseDto getUsers();
}
