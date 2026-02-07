// [REQ-F001] 댓글 리포지토리 (2026-02-06)
package com.demo.blog.repository;

import com.demo.blog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostIdOrderByCreatedAtAsc(Long postId);
}
