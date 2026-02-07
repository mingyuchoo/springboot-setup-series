// [REQ-F001] 댓글 서비스 인터페이스 (2026-02-06)
package com.demo.blog.service;

import com.demo.blog.dto.CommentRequestDto;
import com.demo.blog.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {

    List<CommentResponseDto> findByPostId(Long postId);

    CommentResponseDto create(Long postId, CommentRequestDto requestDto, String username);

    CommentResponseDto update(Long id, CommentRequestDto requestDto, String username);

    void delete(Long id, String username);
}
