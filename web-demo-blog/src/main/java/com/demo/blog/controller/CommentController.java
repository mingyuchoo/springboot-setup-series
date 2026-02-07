// [REQ-F001] 댓글 REST API 컨트롤러 (2026-02-06)
package com.demo.blog.controller;

import com.demo.blog.common.ApiResponse;
import com.demo.blog.dto.CommentRequestDto;
import com.demo.blog.dto.CommentResponseDto;
import com.demo.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> list(@PathVariable Long postId) {
        return ResponseEntity.ok(ApiResponse.success(commentService.findByPostId(postId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseDto>> create(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        CommentResponseDto response = commentService.create(postId, requestDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> update(
            @PathVariable Long postId,
            @PathVariable Long id,
            @Valid @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        CommentResponseDto response = commentService.update(id, requestDto, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long postId,
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        commentService.delete(id, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("댓글이 삭제되었습니다.", null));
    }
}
