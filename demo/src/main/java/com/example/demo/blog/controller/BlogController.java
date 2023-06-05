package com.example.demo.blog.controller;

import com.example.demo.blog.model.Blog;
import com.example.demo.blog.service.BlogService;
import com.example.demo.common.model.JsonReturnDTO;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Validated
public class BlogController {

    private final BlogService blogService;

    @GetMapping(path="/blog/{blogId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JsonReturnDTO<Blog> selectBlogItem(
            @NotNull @PathVariable(name = "blogId") final Integer blogId
    ) throws  Exception {

       Blog searchBlog = Blog.builder().id(blogId).build();

        return JsonReturnDTO
                .<Blog>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(blogService.selectBlogItem(searchBlog))
                .build();
    }

}
