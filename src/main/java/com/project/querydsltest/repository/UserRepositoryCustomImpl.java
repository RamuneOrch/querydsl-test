package com.project.querydsltest.repository;

import com.project.querydsltest.dto.UserResponseDto;
import com.project.querydsltest.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserResponseDto> getUsers() {
        QUser qUser = new QUser("u");
        return queryFactory
            .select(Projections.constructor(UserResponseDto.class,qUser.id, qUser.username))
            .from(qUser)
            .fetch();
    }
}
