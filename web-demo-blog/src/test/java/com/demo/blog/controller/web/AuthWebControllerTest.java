// [REQ-N004] 인증 웹 컨트롤러 슬라이스 테스트 (2026-02-07)
package com.demo.blog.controller.web;

import com.demo.blog.config.SecurityConfig;
import com.demo.blog.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthWebController.class)
@Import(SecurityConfig.class)
class AuthWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    @DisplayName("loginPage_로그인페이지_정상반환")
    void loginPage_로그인페이지_정상반환() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"));
    }

    @Test
    @DisplayName("registerPage_회원가입페이지_정상반환")
    void registerPage_회원가입페이지_정상반환() throws Exception {
        mockMvc.perform(get("/auth/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/register"))
                .andExpect(model().attributeExists("signupRequest"));
    }

    @Test
    @DisplayName("register_정상요청_로그인페이지리다이렉트")
    void register_정상요청_로그인페이지리다이렉트() throws Exception {
        doNothing().when(userService).signup(any());

        mockMvc.perform(post("/auth/register")
                        .with(csrf())
                        .param("username", "newuser")
                        .param("password", "password123")
                        .param("email", "new@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/auth/login"));
    }

    @Test
    @DisplayName("register_빈사용자명_회원가입페이지유지")
    void register_빈사용자명_회원가입페이지유지() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .with(csrf())
                        .param("username", "")
                        .param("password", "password123")
                        .param("email", "new@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/register"));
    }

    @Test
    @DisplayName("register_중복사용자_회원가입페이지유지")
    void register_중복사용자_회원가입페이지유지() throws Exception {
        doThrow(new RuntimeException("이미 사용 중인 사용자명입니다."))
                .when(userService).signup(any());

        mockMvc.perform(post("/auth/register")
                        .with(csrf())
                        .param("username", "existuser")
                        .param("password", "password123")
                        .param("email", "exist@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/register"));
    }
}
