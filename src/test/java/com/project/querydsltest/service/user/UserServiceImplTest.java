package com.project.querydsltest.service.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.project.querydsltest.dto.user.UserPageResponseDto;
import com.project.querydsltest.entity.User;
import com.project.querydsltest.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    @DisplayName("pageable test")
    void getUserByPage() {
        //given
        List<User> userList = Collections.singletonList(new User("test", "test@example.com"));
        Page<User> userPage = new PageImpl<>(userList);
        Pageable pageable = PageRequest.of(1, 3, Sort.by("id"));


        when(userRepository.findAll(
            PageRequest.of(0, 3, Sort.by("id"))
        )).thenReturn(userPage);

        //when
        UserPageResponseDto responseDto = userService.getUserByPage(pageable);

        //then
        assertEquals(1, responseDto.getSize());
        assertEquals(userList.size(), responseDto.getUsers().size());
    }
}
