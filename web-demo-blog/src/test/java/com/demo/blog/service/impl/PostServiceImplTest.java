// [REQ-N003] 포스트 서비스 단위 테스트 (2026-02-07)
package com.demo.blog.service.impl;

import com.demo.blog.domain.Post;
import com.demo.blog.domain.User;
import com.demo.blog.dto.PostRequestDto;
import com.demo.blog.dto.PostResponseDto;
import com.demo.blog.exception.BusinessException;
import com.demo.blog.repository.PostRepository;
import com.demo.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private User testUser;
    private Post testPost;

    @BeforeEach
    void setUp() throws Exception {
        testUser = new User("testuser", "encodedPassword", "test@example.com");
        setField(testUser, "id", 1L);

        testPost = new Post("테스트 제목", "테스트 내용", testUser);
        setField(testPost, "id", 1L);
        setField(testPost, "createdAt", LocalDateTime.now());
        setField(testPost, "updatedAt", LocalDateTime.now());
    }

    @Test
    @DisplayName("findAll_정상요청_페이지반환")
    void findAll_정상요청_페이지반환() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> postPage = new PageImpl<>(List.of(testPost), pageable, 1);
        given(postRepository.findAllByOrderByCreatedAtDesc(pageable)).willReturn(postPage);

        Page<PostResponseDto> result = postService.findAll(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("테스트 제목");
    }

    @Test
    @DisplayName("findById_존재하는ID_정상반환")
    void findById_존재하는ID_정상반환() {
        given(postRepository.findById(1L)).willReturn(Optional.of(testPost));

        PostResponseDto result = postService.findById(1L);

        assertThat(result.getTitle()).isEqualTo("테스트 제목");
        assertThat(result.getAuthorUsername()).isEqualTo("testuser");
    }

    @Test
    @DisplayName("findById_존재하지않는ID_예외발생")
    void findById_존재하지않는ID_예외발생() {
        given(postRepository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> postService.findById(99L))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("포스트를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("create_정상요청_포스트생성")
    void create_정상요청_포스트생성() {
        PostRequestDto requestDto = new PostRequestDto("새 제목", "새 내용");
        given(userRepository.findByUsername("testuser")).willReturn(Optional.of(testUser));
        given(postRepository.save(any(Post.class))).willReturn(testPost);

        PostResponseDto result = postService.create(requestDto, "testuser");

        assertThat(result).isNotNull();
        verify(postRepository).save(any(Post.class));
    }

    @Test
    @DisplayName("create_존재하지않는사용자_예외발생")
    void create_존재하지않는사용자_예외발생() {
        PostRequestDto requestDto = new PostRequestDto("새 제목", "새 내용");
        given(userRepository.findByUsername("unknown")).willReturn(Optional.empty());

        assertThatThrownBy(() -> postService.create(requestDto, "unknown"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("사용자를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("update_본인포스트_정상수정")
    void update_본인포스트_정상수정() {
        PostRequestDto requestDto = new PostRequestDto("수정 제목", "수정 내용");
        given(postRepository.findById(1L)).willReturn(Optional.of(testPost));

        PostResponseDto result = postService.update(1L, requestDto, "testuser");

        assertThat(result.getTitle()).isEqualTo("수정 제목");
        assertThat(result.getContent()).isEqualTo("수정 내용");
    }

    @Test
    @DisplayName("update_타인포스트_예외발생")
    void update_타인포스트_예외발생() {
        PostRequestDto requestDto = new PostRequestDto("수정 제목", "수정 내용");
        given(postRepository.findById(1L)).willReturn(Optional.of(testPost));

        assertThatThrownBy(() -> postService.update(1L, requestDto, "otheruser"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("본인의 포스트만 수정할 수 있습니다.");
    }

    @Test
    @DisplayName("delete_본인포스트_정상삭제")
    void delete_본인포스트_정상삭제() {
        given(postRepository.findById(1L)).willReturn(Optional.of(testPost));

        postService.delete(1L, "testuser");

        verify(postRepository).delete(testPost);
    }

    @Test
    @DisplayName("delete_타인포스트_예외발생")
    void delete_타인포스트_예외발생() {
        given(postRepository.findById(1L)).willReturn(Optional.of(testPost));

        assertThatThrownBy(() -> postService.delete(1L, "otheruser"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("본인의 포스트만 삭제할 수 있습니다.");
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = findField(target.getClass(), fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private Field findField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() != null) {
                return findField(clazz.getSuperclass(), fieldName);
            }
            throw e;
        }
    }
}
