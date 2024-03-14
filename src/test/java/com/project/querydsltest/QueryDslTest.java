package com.project.querydsltest;

import com.project.querydsltest.entity.User;
import com.project.querydsltest.repository.UserRepository;
import com.project.querydsltest.service.user.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class QueryDslTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @PersistenceContext
    EntityManager em;

    @Test
    @Rollback(value = false)
    @Transactional
    public void test1() throws Exception {
        User user = new User();
        for (int i = 0; i < 10; i++) {
            user.setUsername("song" + i);
            user.setPassword("qweQWE1234!!" + i);
            userRepository.save(user);
        }
    }
}
