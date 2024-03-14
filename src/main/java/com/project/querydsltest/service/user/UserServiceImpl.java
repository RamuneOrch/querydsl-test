package com.project.querydsltest.service.user;

import com.project.querydsltest.dto.SignUpRequestDto;
import com.project.querydsltest.dto.UserResponseDto;
import com.project.querydsltest.dto.user.UserPageResponseDto;
import com.project.querydsltest.dto.user.UsersResponseDto;
import com.project.querydsltest.entity.QUser;
import com.project.querydsltest.entity.User;
import com.project.querydsltest.global.exception.PageNotExistenceException;
import com.project.querydsltest.repository.UserRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j(topic = "userService err")
public class UserServiceImpl implements UserService {

    private final JPAQueryFactory queryFactory;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponseDto createUser(SignUpRequestDto req) {
        User user = new User(req.getUsername(), req.getPassword());
        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser);
    }

    @Override
    public UserResponseDto getUser() {
        QUser u = new QUser("u");
        User user = queryFactory
            .selectFrom(u)
            .where(u.username.eq("@email.com"))
            .fetchOne();
        validateUser(user);
        return new UserResponseDto(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new NullPointerException("user가 존재하지 않음");
        }
    }

    @Override
    public UsersResponseDto getUsers() {
        QUser qUser = new QUser("u");
        List<UserResponseDto> users = queryFactory
            .select(Projections.constructor(UserResponseDto.class, qUser.id, qUser.username))
            .from(qUser)
            .fetch();
        return new UsersResponseDto(users);
    }

    @Override
    public UserPageResponseDto getUserByPage(Pageable pageable) throws PageNotExistenceException {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3;

        Page<User> users = userRepository.findAll(
            PageRequest.of(page, pageLimit, pageable.getSort())
        );
        Page<UserResponseDto> usersResponseDtos = users.map(UserResponseDto::new);
        int totalPage = usersResponseDtos.getTotalPages();
        List<UserResponseDto> userLists = usersResponseDtos.getContent();
        validateList(userLists);
        return new UserPageResponseDto(userLists, totalPage);
    }

    private void validateList(List<UserResponseDto> userLists) {
        if (userLists.isEmpty()) {
            throw new PageNotExistenceException("해당 페이지는 존재하지 않습니다.");
        }
    }
}
