package com.project.querydsltest.dto.user;

import com.project.querydsltest.dto.UserResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPageResponseDto {
    private List<UserResponseDto> users;
    private int size;
}
