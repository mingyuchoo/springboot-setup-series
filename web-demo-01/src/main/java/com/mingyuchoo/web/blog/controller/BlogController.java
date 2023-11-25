package com.mingyuchoo.web.blog.controller;

import com.mingyuchoo.web.blog.model.Blog;
import com.mingyuchoo.web.blog.service.BlogService;
import com.mingyuchoo.web.common.response.JsonResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/blogs")
@Validated
public class BlogController {

    private final BlogService blogService;

    @GetMapping(path="/{blogId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JsonResponse<Blog> selectBlogItem(
            @NotNull @PathVariable(name = "blogId") final Integer blogId
    ) throws  Exception {

       Blog searchBlog = Blog.builder().id(blogId).build();

        return JsonResponse
                .<Blog>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(blogService.selectBlogItem(searchBlog))
                .build();
    }

}
