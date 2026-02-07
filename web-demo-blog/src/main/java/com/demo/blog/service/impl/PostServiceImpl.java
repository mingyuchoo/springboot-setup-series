// [REQ-F001] 포스트 서비스 구현체 (2026-02-06)
package com.demo.blog.service.impl;

import com.demo.blog.domain.Post;
import com.demo.blog.domain.User;
import com.demo.blog.dto.PostRequestDto;
import com.demo.blog.dto.PostResponseDto;
import com.demo.blog.exception.BusinessException;
import com.demo.blog.repository.PostRepository;
import com.demo.blog.repository.UserRepository;
import com.demo.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<PostResponseDto> findAll(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(PostResponseDto::from);
    }

    @Override
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException("포스트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        return PostResponseDto.from(post);
    }

    @Override
    @Transactional
    public PostResponseDto create(PostRequestDto requestDto, String username) {
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        Post post = new Post(requestDto.getTitle(), requestDto.getContent(), author);
        postRepository.save(post);
        return PostResponseDto.from(post);
    }

    @Override
    @Transactional
    public PostResponseDto update(Long id, PostRequestDto requestDto, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException("포스트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if (!post.getAuthor().getUsername().equals(username)) {
            throw new BusinessException("본인의 포스트만 수정할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        post.update(requestDto.getTitle(), requestDto.getContent());
        return PostResponseDto.from(post);
    }

    @Override
    @Transactional
    public void delete(Long id, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException("포스트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if (!post.getAuthor().getUsername().equals(username)) {
            throw new BusinessException("본인의 포스트만 삭제할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        postRepository.delete(post);
    }
}
