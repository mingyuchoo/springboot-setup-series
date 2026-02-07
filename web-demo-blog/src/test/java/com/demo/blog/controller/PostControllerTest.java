// [REQ-N003] 포스트 컨트롤러 슬라이스 테스트 (2026-02-07)
package com.demo.blog.controller;

import com.demo.blog.config.SecurityConfig;
import com.demo.blog.dto.PostRequestDto;
import com.demo.blog.dto.PostResponseDto;
import com.demo.blog.service.PostService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

@WebMvcTest(PostController.class)
@Import(SecurityConfig.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PostService postService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("list_인증된사용자_포스트목록반환")
    void list_인증된사용자_포스트목록반환() throws Exception {
        mockUserDetails("testuser");
        Page<PostResponseDto> emptyPage = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);
        given(postService.findAll(any())).willReturn(emptyPage);

        mockMvc.perform(get("/api/posts")
                        .with(httpBasic("testuser", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("list_미인증사용자_401반환")
    void list_미인증사용자_401반환() throws Exception {
        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("detail_인증된사용자_포스트상세반환")
    void detail_인증된사용자_포스트상세반환() throws Exception {
        mockUserDetails("testuser");
        PostResponseDto mockResponse = createMockPostResponseDto();
        given(postService.findById(1L)).willReturn(mockResponse);

        mockMvc.perform(get("/api/posts/1")
                        .with(httpBasic("testuser", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("create_인증된사용자_201반환")
    void create_인증된사용자_201반환() throws Exception {
        mockUserDetails("testuser");
        PostRequestDto requestDto = new PostRequestDto("새 제목", "새 내용");
        PostResponseDto mockResponse = createMockPostResponseDto();
        given(postService.create(any(PostRequestDto.class), eq("testuser"))).willReturn(mockResponse);

        mockMvc.perform(post("/api/posts")
                        .with(httpBasic("testuser", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("create_빈제목_400반환")
    void create_빈제목_400반환() throws Exception {
        mockUserDetails("testuser");
        PostRequestDto requestDto = new PostRequestDto("", "내용");

        mockMvc.perform(post("/api/posts")
                        .with(httpBasic("testuser", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("update_인증된사용자_정상수정")
    void update_인증된사용자_정상수정() throws Exception {
        mockUserDetails("testuser");
        PostRequestDto requestDto = new PostRequestDto("수정 제목", "수정 내용");
        PostResponseDto mockResponse = createMockPostResponseDto();
        given(postService.update(eq(1L), any(PostRequestDto.class), eq("testuser"))).willReturn(mockResponse);

        mockMvc.perform(put("/api/posts/1")
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
        doNothing().when(postService).delete(1L, "testuser");

        mockMvc.perform(delete("/api/posts/1")
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

    private PostResponseDto createMockPostResponseDto() {
        try {
            PostResponseDto dto = new PostResponseDto();
            var idField = PostResponseDto.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(dto, 1L);

            var titleField = PostResponseDto.class.getDeclaredField("title");
            titleField.setAccessible(true);
            titleField.set(dto, "테스트 제목");

            var contentField = PostResponseDto.class.getDeclaredField("content");
            contentField.setAccessible(true);
            contentField.set(dto, "테스트 내용");

            var authorField = PostResponseDto.class.getDeclaredField("authorUsername");
            authorField.setAccessible(true);
            authorField.set(dto, "testuser");

            var createdAtField = PostResponseDto.class.getDeclaredField("createdAt");
            createdAtField.setAccessible(true);
            createdAtField.set(dto, LocalDateTime.now());

            var updatedAtField = PostResponseDto.class.getDeclaredField("updatedAt");
            updatedAtField.setAccessible(true);
            updatedAtField.set(dto, LocalDateTime.now());

            return dto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
