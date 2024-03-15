package com.project.querydsltest.dto.user;

import com.project.querydsltest.dto.UserResponseDto;
import com.project.querydsltest.entity.User;
import com.querydsl.core.Tuple;
import java.util.List;
import lombok.Getter;

@Getter
public class UsersResponseDto {
    private List<UserResponseDto> users;
    public UsersResponseDto(List<UserResponseDto> users){
        this.users = users;
    }
}
