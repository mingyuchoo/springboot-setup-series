// [REQ-F001] 사용자 서비스 인터페이스 (2026-02-06)
package com.demo.blog.service;

import com.demo.blog.dto.SignupRequestDto;

public interface UserService {

    void signup(SignupRequestDto requestDto);
}
