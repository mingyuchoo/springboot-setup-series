// [REQ-F001] 포스트 REST API 컨트롤러 (2026-02-06)
package com.demo.blog.controller;

import com.demo.blog.common.ApiResponse;
import com.demo.blog.dto.PostRequestDto;
import com.demo.blog.dto.PostResponseDto;
import com.demo.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PostResponseDto>>> list(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(postService.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> detail(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(postService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponseDto>> create(
            @Valid @RequestBody PostRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        PostResponseDto response = postService.create(requestDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> update(
            @PathVariable Long id,
            @Valid @RequestBody PostRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        PostResponseDto response = postService.update(id, requestDto, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        postService.delete(id, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("포스트가 삭제되었습니다.", null));
    }
}
