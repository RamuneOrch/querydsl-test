package com.project.querydsltest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.querydsltest.dto.SignUpRequestDto;
import com.project.querydsltest.dto.UserResponseDto;
import com.project.querydsltest.entity.User;
import com.project.querydsltest.repository.UserRepository;
import com.project.querydsltest.service.user.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class QueryDslTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserService userService;
    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void test1() throws Exception {
        //given
        String username = "spring web";
        String password = "qwer1234!!";
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto(username, password);

        // 가짜 응답 객체 생성
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUsername("spring web");

        // userService.createUser 메서드가 호출될 때 가짜 응답 객체를 반환하도록 설정
        when(userService.createUser(any(SignUpRequestDto.class))).thenReturn(userResponseDto);

        // POST 요청 보내기
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").value("회원가입이 성공하였습니다."))
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.username").value("testUser"));
    }
}
