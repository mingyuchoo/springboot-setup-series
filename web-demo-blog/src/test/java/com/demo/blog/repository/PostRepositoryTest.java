// [REQ-N003] 포스트 리포지토리 슬라이스 테스트 (2026-02-07)
package com.demo.blog.repository;

import com.demo.blog.domain.Post;
import com.demo.blog.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("testuser", "encodedPassword", "test@example.com");
        entityManager.persistAndFlush(testUser);
    }

    @Test
    @DisplayName("findAllByOrderByCreatedAtDesc_포스트존재_최신순정렬반환")
    void findAllByOrderByCreatedAtDesc_포스트존재_최신순정렬반환() {
        Post post1 = new Post("첫 번째 포스트", "내용1", testUser);
        entityManager.persistAndFlush(post1);

        Post post2 = new Post("두 번째 포스트", "내용2", testUser);
        entityManager.persistAndFlush(post2);

        Page<Post> result = postRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(2);
        // 최신순 정렬 확인: 두 번째 포스트가 먼저
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("두 번째 포스트");
    }

    @Test
    @DisplayName("findAllByOrderByCreatedAtDesc_포스트없음_빈페이지반환")
    void findAllByOrderByCreatedAtDesc_포스트없음_빈페이지반환() {
        Page<Post> result = postRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, 10));

        assertThat(result.getContent()).isEmpty();
        assertThat(result.getTotalElements()).isZero();
    }

    @Test
    @DisplayName("findById_저장된포스트_정상조회")
    void findById_저장된포스트_정상조회() {
        Post post = new Post("테스트 포스트", "내용", testUser);
        Post saved = entityManager.persistAndFlush(post);

        var result = postRepository.findById(saved.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("테스트 포스트");
    }

    @Test
    @DisplayName("save_새포스트_정상저장")
    void save_새포스트_정상저장() {
        Post post = new Post("새 포스트", "새 내용", testUser);

        Post saved = postRepository.save(post);
        entityManager.flush();

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("delete_존재하는포스트_정상삭제")
    void delete_존재하는포스트_정상삭제() {
        Post post = new Post("삭제할 포스트", "내용", testUser);
        Post saved = entityManager.persistAndFlush(post);

        postRepository.delete(saved);
        entityManager.flush();

        assertThat(postRepository.findById(saved.getId())).isEmpty();
    }
}
