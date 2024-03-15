package com.project.querydsltest.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import com.project.querydsltest.TestConfig;
import com.project.querydsltest.dto.UserResponseDto;
import com.project.querydsltest.entity.QUser;
import com.project.querydsltest.entity.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TestConfig.class)
class UserRepositoryCustomImplTest {

    @Autowired
    UserRepositoryCustomImpl userRepositoryCustom;

    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired
    UserRepository userRepository;

    @Test
    void getUsers() {
        //given
        User user = new User();
        user.setUsername("song");
        user.setPassword("qwer1234!!@#");
        userRepository.save(user);
        //when
        List<UserResponseDto> users = userRepositoryCustom.getUsers();
        //then
        assertEquals(1, users.size());
    }
}
