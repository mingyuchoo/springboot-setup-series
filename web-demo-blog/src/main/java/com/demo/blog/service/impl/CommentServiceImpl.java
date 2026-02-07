// [REQ-F001] 댓글 서비스 구현체 (2026-02-06)
package com.demo.blog.service.impl;

import com.demo.blog.domain.Comment;
import com.demo.blog.domain.Post;
import com.demo.blog.domain.User;
import com.demo.blog.dto.CommentRequestDto;
import com.demo.blog.dto.CommentResponseDto;
import com.demo.blog.exception.BusinessException;
import com.demo.blog.repository.CommentRepository;
import com.demo.blog.repository.PostRepository;
import com.demo.blog.repository.UserRepository;
import com.demo.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              PostRepository postRepository,
                              UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CommentResponseDto> findByPostId(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId).stream()
                .map(CommentResponseDto::from)
                .toList();
    }

    @Override
    @Transactional
    public CommentResponseDto create(Long postId, CommentRequestDto requestDto, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException("포스트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        Comment comment = new Comment(requestDto.getContent(), author, post);
        commentRepository.save(comment);
        return CommentResponseDto.from(comment);
    }

    @Override
    @Transactional
    public CommentResponseDto update(Long id, CommentRequestDto requestDto, String username) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if (!comment.getAuthor().getUsername().equals(username)) {
            throw new BusinessException("본인의 댓글만 수정할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        comment.update(requestDto.getContent());
        return CommentResponseDto.from(comment);
    }

    @Override
    @Transactional
    public void delete(Long id, String username) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if (!comment.getAuthor().getUsername().equals(username)) {
            throw new BusinessException("본인의 댓글만 삭제할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        commentRepository.delete(comment);
    }
}
