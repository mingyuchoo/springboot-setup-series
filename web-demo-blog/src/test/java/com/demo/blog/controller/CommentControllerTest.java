// [REQ-N003] 댓글 컨트롤러 슬라이스 테스트 (2026-02-07)
package com.demo.blog.controller;

import com.demo.blog.config.SecurityConfig;
import com.demo.blog.dto.CommentRequestDto;
import com.demo.blog.dto.CommentResponseDto;
import com.demo.blog.service.CommentService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@Import(SecurityConfig.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CommentService commentService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("list_인증된사용자_댓글목록반환")
    void list_인증된사용자_댓글목록반환() throws Exception {
        mockUserDetails("testuser");
        given(commentService.findByPostId(1L)).willReturn(List.of());

        mockMvc.perform(get("/api/posts/1/comments")
                        .with(httpBasic("testuser", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("list_미인증사용자_401반환")
    void list_미인증사용자_401반환() throws Exception {
        mockMvc.perform(get("/api/posts/1/comments"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("create_인증된사용자_201반환")
    void create_인증된사용자_201반환() throws Exception {
        mockUserDetails("testuser");
        CommentRequestDto requestDto = new CommentRequestDto("새 댓글");
        CommentResponseDto mockResponse = createMockCommentResponseDto();
        given(commentService.create(eq(1L), any(CommentRequestDto.class), eq("testuser")))
                .willReturn(mockResponse);

        mockMvc.perform(post("/api/posts/1/comments")
                        .with(httpBasic("testuser", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("create_빈내용_400반환")
    void create_빈내용_400반환() throws Exception {
        mockUserDetails("testuser");
        CommentRequestDto requestDto = new CommentRequestDto("");

        mockMvc.perform(post("/api/posts/1/comments")
                        .with(httpBasic("testuser", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("update_인증된사용자_정상수정")
    void update_인증된사용자_정상수정() throws Exception {
        mockUserDetails("testuser");
        CommentRequestDto requestDto = new CommentRequestDto("수정된 댓글");
        CommentResponseDto mockResponse = createMockCommentResponseDto();
        given(commentService.update(eq(1L), any(CommentRequestDto.class), eq("testuser")))
                .willReturn(mockResponse);

        mockMvc.perform(put("/api/posts/1/comments/1")
                        .with(httpBasic("testuser", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("delete_인증된사용자_정상삭제")
    void delete_인증된사용자_정상삭제() throws Exception {
        mockUserDetails("testuser");
        doNothing().when(commentService).delete(1L, "testuser");

        mockMvc.perform(delete("/api/posts/1/comments/1")
                        .with(httpBasic("testuser", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    private void mockUserDetails(String username) {
        given(userDetailsService.loadUserByUsername(username))
                .willReturn(org.springframework.security.core.userdetails.User
                        .withUsername(username)
                        .password(passwordEncoder.encode("password"))
                        .roles("USER")
                        .build());
    }

    private CommentResponseDto createMockCommentResponseDto() {
        try {
            CommentResponseDto dto = new CommentResponseDto();
            var idField = CommentResponseDto.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(dto, 1L);

            var contentField = CommentResponseDto.class.getDeclaredField("content");
            contentField.setAccessible(true);
            contentField.set(dto, "테스트 댓글");

            var authorField = CommentResponseDto.class.getDeclaredField("authorUsername");
            authorField.setAccessible(true);
            authorField.set(dto, "testuser");

            var postIdField = CommentResponseDto.class.getDeclaredField("postId");
            postIdField.setAccessible(true);
            postIdField.set(dto, 1L);

            var createdAtField = CommentResponseDto.class.getDeclaredField("createdAt");
            createdAtField.setAccessible(true);
            createdAtField.set(dto, LocalDateTime.now());

            var updatedAtField = CommentResponseDto.class.getDeclaredField("updatedAt");
            updatedAtField.setAccessible(true);
            updatedAtField.set(dto, LocalDateTime.now());

            return dto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
