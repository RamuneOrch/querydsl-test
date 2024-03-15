package com.project.querydsltest.dto;

import com.project.querydsltest.entity.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.Query;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
