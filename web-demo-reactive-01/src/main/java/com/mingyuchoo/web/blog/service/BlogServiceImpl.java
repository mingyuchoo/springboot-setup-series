package com.mingyuchoo.web.blog.service;

import com.mingyuchoo.web.blog.model.Blog;
import com.mingyuchoo.web.blog.repository.BlogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("blogService")
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    @Autowired
    private final BlogMapper blogMapper;

    @Override
    public Blog selectBlogItem(Blog blog) throws Exception {
        return blogMapper.selectBlogItem(blog);
    }
}
