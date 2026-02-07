// [REQ-N003] 사용자 서비스 단위 테스트 (2026-02-07)
package com.demo.blog.service.impl;

import com.demo.blog.domain.User;
import com.demo.blog.dto.SignupRequestDto;
import com.demo.blog.exception.BusinessException;
import com.demo.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private SignupRequestDto signupRequest;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequestDto();
        signupRequest.setUsername("newuser");
        signupRequest.setPassword("password123");
        signupRequest.setEmail("new@example.com");
    }

    @Test
    @DisplayName("signup_정상요청_회원가입성공")
    void signup_정상요청_회원가입성공() {
        given(userRepository.existsByUsername("newuser")).willReturn(false);
        given(userRepository.existsByEmail("new@example.com")).willReturn(false);
        given(passwordEncoder.encode(anyString())).willReturn("encodedPassword");

        userService.signup(signupRequest);

        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("signup_중복사용자명_예외발생")
    void signup_중복사용자명_예외발생() {
        given(userRepository.existsByUsername("newuser")).willReturn(true);

        assertThatThrownBy(() -> userService.signup(signupRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("이미 사용 중인 사용자명입니다.");
    }

    @Test
    @DisplayName("signup_중복이메일_예외발생")
    void signup_중복이메일_예외발생() {
        given(userRepository.existsByUsername("newuser")).willReturn(false);
        given(userRepository.existsByEmail("new@example.com")).willReturn(true);

        assertThatThrownBy(() -> userService.signup(signupRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("이미 사용 중인 이메일입니다.");
    }

    @Test
    @DisplayName("loadUserByUsername_존재하는사용자_UserDetails반환")
    void loadUserByUsername_존재하는사용자_UserDetails반환() {
        User user = new User("testuser", "encodedPassword", "test@example.com");
        given(userRepository.findByUsername("testuser")).willReturn(Optional.of(user));

        UserDetails result = userService.loadUserByUsername("testuser");

        assertThat(result.getUsername()).isEqualTo("testuser");
        assertThat(result.getAuthorities()).hasSize(1);
    }

    @Test
    @DisplayName("loadUserByUsername_존재하지않는사용자_예외발생")
    void loadUserByUsername_존재하지않는사용자_예외발생() {
        given(userRepository.findByUsername("unknown")).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.loadUserByUsername("unknown"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
