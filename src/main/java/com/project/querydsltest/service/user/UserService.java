package com.project.querydsltest.service.user;

import com.project.querydsltest.dto.SignUpRequestDto;
import com.project.querydsltest.dto.UserResponseDto;
import com.project.querydsltest.dto.user.UserPageResponseDto;
import com.project.querydsltest.dto.user.UsersResponseDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface UserService {
    UserResponseDto createUser(SignUpRequestDto req);

    UserResponseDto getUser();

    UsersResponseDto getUsers();

    UserPageResponseDto getUserByPage(Pageable pageable);
}
