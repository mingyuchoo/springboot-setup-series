// [REQ-F001] 포스트 서비스 인터페이스 (2026-02-06)
package com.demo.blog.service;

import com.demo.blog.dto.PostRequestDto;
import com.demo.blog.dto.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Page<PostResponseDto> findAll(Pageable pageable);

    PostResponseDto findById(Long id);

    PostResponseDto create(PostRequestDto requestDto, String username);

    PostResponseDto update(Long id, PostRequestDto requestDto, String username);

    void delete(Long id, String username);
}
