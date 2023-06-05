package com.example.demo.blog.service;

import com.example.demo.blog.model.Blog;
import com.example.demo.blog.repository.BlogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("blogService")
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogMapper blogMapper;

    @Override
    public Blog selectBlogItem(Blog blog) throws Exception {
        return blogMapper.selectBlogItem(blog);
    }

}
