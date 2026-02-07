// [REQ-N005] 샘플 데이터 초기화 테스트 (2026-02-07)
package com.demo.blog.config;

import com.demo.blog.domain.User;
import com.demo.blog.repository.CommentRepository;
import com.demo.blog.repository.PostRepository;
import com.demo.blog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DataInitializerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void run_애플리케이션시작_사용자100명생성됨() {
        assertThat(userRepository.count()).isEqualTo(100);
    }

    @Test
    void run_애플리케이션시작_포스트100개생성됨() {
        assertThat(postRepository.count()).isEqualTo(100);
    }

    @Test
    void run_애플리케이션시작_댓글100개생성됨() {
        assertThat(commentRepository.count()).isEqualTo(100);
    }

    @Test
    void run_샘플사용자_패스워드일치() {
        User user = userRepository.findByUsername("user1").orElseThrow();
        assertThat(passwordEncoder.matches("password", user.getPassword())).isTrue();
    }

    @Test
    void run_샘플사용자_이메일형식검증() {
        User user = userRepository.findByUsername("user50").orElseThrow();
        assertThat(user.getEmail()).isEqualTo("user50@example.com");
    }
}
