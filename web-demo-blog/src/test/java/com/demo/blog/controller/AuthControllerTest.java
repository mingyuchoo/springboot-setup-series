// [REQ-N003] 인증 컨트롤러 슬라이스 테스트 (2026-02-07)
package com.demo.blog.controller;

import com.demo.blog.config.SecurityConfig;
import com.demo.blog.dto.SignupRequestDto;
import com.demo.blog.exception.BusinessException;
import com.demo.blog.service.UserService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    @DisplayName("signup_정상요청_201반환")
    void signup_정상요청_201반환() throws Exception {
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setUsername("newuser");
        requestDto.setPassword("password123");
        requestDto.setEmail("new@example.com");

        doNothing().when(userService).signup(any(SignupRequestDto.class));

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("회원가입이 완료되었습니다."));
    }

    @Test
    @DisplayName("signup_중복사용자명_400반환")
    void signup_중복사용자명_400반환() throws Exception {
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setUsername("existuser");
        requestDto.setPassword("password123");
        requestDto.setEmail("exist@example.com");

        doThrow(new BusinessException("이미 사용 중인 사용자명입니다."))
                .when(userService).signup(any(SignupRequestDto.class));

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @DisplayName("signup_빈사용자명_400반환")
    void signup_빈사용자명_400반환() throws Exception {
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setUsername("");
        requestDto.setPassword("password123");
        requestDto.setEmail("new@example.com");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("signup_잘못된이메일형식_400반환")
    void signup_잘못된이메일형식_400반환() throws Exception {
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setUsername("newuser");
        requestDto.setPassword("password123");
        requestDto.setEmail("invalid-email");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }
}
