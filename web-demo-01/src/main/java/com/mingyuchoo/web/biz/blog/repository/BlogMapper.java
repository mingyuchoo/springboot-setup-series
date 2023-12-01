package com.mingyuchoo.web.biz.blog.repository;

import com.mingyuchoo.web.biz.blog.model.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface BlogMapper {

    @Transactional(readOnly = true)
    Blog selectBlogItem(Blog blog) throws Exception;

}
