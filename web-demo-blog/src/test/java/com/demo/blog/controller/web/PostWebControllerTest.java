// [REQ-N004] 포스트 웹 컨트롤러 슬라이스 테스트 (2026-02-07)
package com.demo.blog.controller.web;

import com.demo.blog.config.SecurityConfig;
import com.demo.blog.dto.CommentResponseDto;
import com.demo.blog.dto.PostResponseDto;
import com.demo.blog.service.CommentService;
import com.demo.blog.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostWebController.class)
@Import(SecurityConfig.class)
class PostWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @MockitoBean
    private CommentService commentService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    @DisplayName("list_인증된사용자_포스트목록페이지반환")
    void list_인증된사용자_포스트목록페이지반환() throws Exception {
        Page<PostResponseDto> emptyPage = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);
        given(postService.findAll(any())).willReturn(emptyPage);

        mockMvc.perform(get("/posts").with(user("testuser").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("post/list"))
                .andExpect(model().attributeExists("posts"));
    }

    @Test
    @DisplayName("list_미인증사용자_로그인페이지리다이렉트")
    void list_미인증사용자_로그인페이지리다이렉트() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("detail_인증된사용자_포스트상세페이지반환")
    void detail_인증된사용자_포스트상세페이지반환() throws Exception {
        PostResponseDto mockPost = createMockPostResponseDto();
        given(postService.findById(1L)).willReturn(mockPost);
        given(commentService.findByPostId(1L)).willReturn(List.of());

        mockMvc.perform(get("/posts/1").with(user("testuser").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("post/detail"))
                .andExpect(model().attributeExists("post", "comments", "commentRequest", "currentUsername"));
    }

    @Test
    @DisplayName("createForm_인증된사용자_작성폼반환")
    void createForm_인증된사용자_작성폼반환() throws Exception {
        mockMvc.perform(get("/posts/new").with(user("testuser").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("post/form"))
                .andExpect(model().attributeExists("postRequest"));
    }

    @Test
    @DisplayName("create_정상요청_포스트상세리다이렉트")
    void create_정상요청_포스트상세리다이렉트() throws Exception {
        PostResponseDto mockPost = createMockPostResponseDto();
        given(postService.create(any(), eq("testuser"))).willReturn(mockPost);

        mockMvc.perform(post("/posts")
                        .with(user("testuser").roles("USER"))
                        .with(csrf())
                        .param("title", "새 포스트 제목")
                        .param("content", "새 포스트 내용"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

    @Test
    @DisplayName("create_빈제목_작성폼유지")
    void create_빈제목_작성폼유지() throws Exception {
        mockMvc.perform(post("/posts")
                        .with(user("testuser").roles("USER"))
                        .with(csrf())
                        .param("title", "")
                        .param("content", "내용"))
                .andExpect(status().isOk())
                .andExpect(view().name("post/form"));
    }

    @Test
    @DisplayName("editForm_인증된사용자_수정폼반환")
    void editForm_인증된사용자_수정폼반환() throws Exception {
        PostResponseDto mockPost = createMockPostResponseDto();
        given(postService.findById(1L)).willReturn(mockPost);

        mockMvc.perform(get("/posts/1/edit").with(user("testuser").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("post/form"))
                .andExpect(model().attributeExists("postRequest", "postId"));
    }

    @Test
    @DisplayName("update_정상요청_포스트상세리다이렉트")
    void update_정상요청_포스트상세리다이렉트() throws Exception {
        PostResponseDto mockPost = createMockPostResponseDto();
        given(postService.update(eq(1L), any(), eq("testuser"))).willReturn(mockPost);

        mockMvc.perform(post("/posts/1")
                        .with(user("testuser").roles("USER"))
                        .with(csrf())
                        .param("title", "수정된 제목")
                        .param("content", "수정된 내용"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

    @Test
    @DisplayName("update_빈제목_수정폼유지")
    void update_빈제목_수정폼유지() throws Exception {
        mockMvc.perform(post("/posts/1")
                        .with(user("testuser").roles("USER"))
                        .with(csrf())
                        .param("title", "")
                        .param("content", "내용"))
                .andExpect(status().isOk())
                .andExpect(view().name("post/form"))
                .andExpect(model().attributeExists("postId"));
    }

    @Test
    @DisplayName("delete_인증된사용자_목록리다이렉트")
    void delete_인증된사용자_목록리다이렉트() throws Exception {
        doNothing().when(postService).delete(1L, "testuser");

        mockMvc.perform(post("/posts/1/delete")
                        .with(user("testuser").roles("USER"))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    @DisplayName("createComment_정상요청_포스트상세리다이렉트")
    void createComment_정상요청_포스트상세리다이렉트() throws Exception {
        given(commentService.create(eq(1L), any(), eq("testuser")))
                .willReturn(new CommentResponseDto());

        mockMvc.perform(post("/posts/1/comments")
                        .with(user("testuser").roles("USER"))
                        .with(csrf())
                        .param("content", "댓글 내용"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

    @Test
    @DisplayName("deleteComment_인증된사용자_포스트상세리다이렉트")
    void deleteComment_인증된사용자_포스트상세리다이렉트() throws Exception {
        doNothing().when(commentService).delete(1L, "testuser");

        mockMvc.perform(post("/posts/1/comments/1/delete")
                        .with(user("testuser").roles("USER"))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
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
