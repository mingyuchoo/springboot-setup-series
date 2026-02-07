// [REQ-N003] 댓글 서비스 단위 테스트 (2026-02-07)
package com.demo.blog.service.impl;

import com.demo.blog.domain.Comment;
import com.demo.blog.domain.Post;
import com.demo.blog.domain.User;
import com.demo.blog.dto.CommentRequestDto;
import com.demo.blog.dto.CommentResponseDto;
import com.demo.blog.exception.BusinessException;
import com.demo.blog.repository.CommentRepository;
import com.demo.blog.repository.PostRepository;
import com.demo.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    private User testUser;
    private Post testPost;
    private Comment testComment;

    @BeforeEach
    void setUp() throws Exception {
        testUser = new User("testuser", "encodedPassword", "test@example.com");
        setField(testUser, "id", 1L);

        testPost = new Post("테스트 제목", "테스트 내용", testUser);
        setField(testPost, "id", 1L);
        setField(testPost, "createdAt", LocalDateTime.now());
        setField(testPost, "updatedAt", LocalDateTime.now());

        testComment = new Comment("테스트 댓글", testUser, testPost);
        setField(testComment, "id", 1L);
        setField(testComment, "createdAt", LocalDateTime.now());
        setField(testComment, "updatedAt", LocalDateTime.now());
    }

    @Test
    @DisplayName("findByPostId_정상요청_댓글목록반환")
    void findByPostId_정상요청_댓글목록반환() {
        given(commentRepository.findByPostIdOrderByCreatedAtAsc(1L))
                .willReturn(List.of(testComment));

        List<CommentResponseDto> result = commentService.findByPostId(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getContent()).isEqualTo("테스트 댓글");
    }

    @Test
    @DisplayName("create_정상요청_댓글생성")
    void create_정상요청_댓글생성() {
        CommentRequestDto requestDto = new CommentRequestDto("새 댓글");
        given(postRepository.findById(1L)).willReturn(Optional.of(testPost));
        given(userRepository.findByUsername("testuser")).willReturn(Optional.of(testUser));
        given(commentRepository.save(any(Comment.class))).willReturn(testComment);

        CommentResponseDto result = commentService.create(1L, requestDto, "testuser");

        assertThat(result).isNotNull();
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    @DisplayName("create_존재하지않는포스트_예외발생")
    void create_존재하지않는포스트_예외발생() {
        CommentRequestDto requestDto = new CommentRequestDto("새 댓글");
        given(postRepository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.create(99L, requestDto, "testuser"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("포스트를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("create_존재하지않는사용자_예외발생")
    void create_존재하지않는사용자_예외발생() {
        CommentRequestDto requestDto = new CommentRequestDto("새 댓글");
        given(postRepository.findById(1L)).willReturn(Optional.of(testPost));
        given(userRepository.findByUsername("unknown")).willReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.create(1L, requestDto, "unknown"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("사용자를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("update_본인댓글_정상수정")
    void update_본인댓글_정상수정() {
        CommentRequestDto requestDto = new CommentRequestDto("수정된 댓글");
        given(commentRepository.findById(1L)).willReturn(Optional.of(testComment));

        CommentResponseDto result = commentService.update(1L, requestDto, "testuser");

        assertThat(result.getContent()).isEqualTo("수정된 댓글");
    }

    @Test
    @DisplayName("update_타인댓글_예외발생")
    void update_타인댓글_예외발생() {
        CommentRequestDto requestDto = new CommentRequestDto("수정된 댓글");
        given(commentRepository.findById(1L)).willReturn(Optional.of(testComment));

        assertThatThrownBy(() -> commentService.update(1L, requestDto, "otheruser"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("본인의 댓글만 수정할 수 있습니다.");
    }

    @Test
    @DisplayName("delete_본인댓글_정상삭제")
    void delete_본인댓글_정상삭제() {
        given(commentRepository.findById(1L)).willReturn(Optional.of(testComment));

        commentService.delete(1L, "testuser");

        verify(commentRepository).delete(testComment);
    }

    @Test
    @DisplayName("delete_타인댓글_예외발생")
    void delete_타인댓글_예외발생() {
        given(commentRepository.findById(1L)).willReturn(Optional.of(testComment));

        assertThatThrownBy(() -> commentService.delete(1L, "otheruser"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("본인의 댓글만 삭제할 수 있습니다.");
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
