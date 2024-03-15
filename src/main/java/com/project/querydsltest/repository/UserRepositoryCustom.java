package com.project.querydsltest.repository;

import com.project.querydsltest.dto.UserResponseDto;
import java.util.List;

public interface UserRepositoryCustom {
    List<UserResponseDto> getUsers();
}
