// [REQ-F001] 댓글 응답 DTO (2026-02-06)
package com.demo.blog.dto;

import com.demo.blog.domain.Comment;

import java.time.LocalDateTime;

public class CommentResponseDto {

    private Long id;
    private String content;
    private String authorUsername;
    private Long postId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentResponseDto() {
    }

    public static CommentResponseDto from(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.id = comment.getId();
        dto.content = comment.getContent();
        dto.authorUsername = comment.getAuthor().getUsername();
        dto.postId = comment.getPost().getId();
        dto.createdAt = comment.getCreatedAt();
        dto.updatedAt = comment.getUpdatedAt();
        return dto;
    }

    // Getters
    public Long getId() { return id; }
    public String getContent() { return content; }
    public String getAuthorUsername() { return authorUsername; }
    public Long getPostId() { return postId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
