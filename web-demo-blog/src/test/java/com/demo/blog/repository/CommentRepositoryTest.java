// [REQ-N003] 댓글 리포지토리 슬라이스 테스트 (2026-02-07)
package com.demo.blog.repository;

import com.demo.blog.domain.Comment;
import com.demo.blog.domain.Post;
import com.demo.blog.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommentRepository commentRepository;

    private User testUser;
    private Post testPost;

    @BeforeEach
    void setUp() {
        testUser = new User("testuser", "encodedPassword", "test@example.com");
        entityManager.persistAndFlush(testUser);

        testPost = new Post("테스트 포스트", "내용", testUser);
        entityManager.persistAndFlush(testPost);
    }

    @Test
    @DisplayName("findByPostIdOrderByCreatedAtAsc_댓글존재_시간순정렬반환")
    void findByPostIdOrderByCreatedAtAsc_댓글존재_시간순정렬반환() {
        Comment comment1 = new Comment("첫 번째 댓글", testUser, testPost);
        entityManager.persistAndFlush(comment1);

        Comment comment2 = new Comment("두 번째 댓글", testUser, testPost);
        entityManager.persistAndFlush(comment2);

        List<Comment> result = commentRepository.findByPostIdOrderByCreatedAtAsc(testPost.getId());

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getContent()).isEqualTo("첫 번째 댓글");
        assertThat(result.get(1).getContent()).isEqualTo("두 번째 댓글");
    }

    @Test
    @DisplayName("findByPostIdOrderByCreatedAtAsc_댓글없음_빈목록반환")
    void findByPostIdOrderByCreatedAtAsc_댓글없음_빈목록반환() {
        List<Comment> result = commentRepository.findByPostIdOrderByCreatedAtAsc(testPost.getId());

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("findByPostIdOrderByCreatedAtAsc_다른포스트댓글제외_해당포스트만반환")
    void findByPostIdOrderByCreatedAtAsc_다른포스트댓글제외_해당포스트만반환() {
        Post otherPost = new Post("다른 포스트", "다른 내용", testUser);
        entityManager.persistAndFlush(otherPost);

        Comment comment1 = new Comment("이 포스트 댓글", testUser, testPost);
        entityManager.persistAndFlush(comment1);

        Comment comment2 = new Comment("다른 포스트 댓글", testUser, otherPost);
        entityManager.persistAndFlush(comment2);

        List<Comment> result = commentRepository.findByPostIdOrderByCreatedAtAsc(testPost.getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getContent()).isEqualTo("이 포스트 댓글");
    }

    @Test
    @DisplayName("save_새댓글_정상저장")
    void save_새댓글_정상저장() {
        Comment comment = new Comment("새 댓글", testUser, testPost);

        Comment saved = commentRepository.save(comment);
        entityManager.flush();

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
    }
}
