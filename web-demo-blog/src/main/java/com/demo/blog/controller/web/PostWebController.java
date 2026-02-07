// [REQ-F001] 포스트 웹 컨트롤러 (2026-02-06)
package com.demo.blog.controller.web;

import com.demo.blog.dto.CommentRequestDto;
import com.demo.blog.dto.PostRequestDto;
import com.demo.blog.dto.PostResponseDto;
import com.demo.blog.service.CommentService;
import com.demo.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/posts")
public class PostWebController {

    private final PostService postService;
    private final CommentService commentService;

    public PostWebController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping
    public String list(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<PostResponseDto> posts = postService.findAll(pageable);
        model.addAttribute("posts", posts);
        return "post/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model,
                         @AuthenticationPrincipal UserDetails userDetails) {
        PostResponseDto post = postService.findById(id);
        model.addAttribute("post", post);
        model.addAttribute("comments", commentService.findByPostId(id));
        model.addAttribute("commentRequest", new CommentRequestDto());
        model.addAttribute("currentUsername", userDetails.getUsername());
        return "post/detail";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("postRequest", new PostRequestDto());
        return "post/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("postRequest") PostRequestDto requestDto,
                         BindingResult bindingResult,
                         @AuthenticationPrincipal UserDetails userDetails,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "post/form";
        }

        PostResponseDto post = postService.create(requestDto, userDetails.getUsername());
        redirectAttributes.addFlashAttribute("message", "포스트가 작성되었습니다.");
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {
        PostResponseDto post = postService.findById(id);
        PostRequestDto requestDto = new PostRequestDto(post.getTitle(), post.getContent());
        model.addAttribute("postRequest", requestDto);
        model.addAttribute("postId", id);
        return "post/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("postRequest") PostRequestDto requestDto,
                         BindingResult bindingResult,
                         @AuthenticationPrincipal UserDetails userDetails,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("postId", id);
            return "post/form";
        }

        postService.update(id, requestDto, userDetails.getUsername());
        redirectAttributes.addFlashAttribute("message", "포스트가 수정되었습니다.");
        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         @AuthenticationPrincipal UserDetails userDetails,
                         RedirectAttributes redirectAttributes) {
        postService.delete(id, userDetails.getUsername());
        redirectAttributes.addFlashAttribute("message", "포스트가 삭제되었습니다.");
        return "redirect:/posts";
    }

    @PostMapping("/{postId}/comments")
    public String createComment(@PathVariable Long postId,
                                @Valid @ModelAttribute("commentRequest") CommentRequestDto requestDto,
                                BindingResult bindingResult,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasErrors()) {
            commentService.create(postId, requestDto, userDetails.getUsername());
        }
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/{postId}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable Long postId,
                                @PathVariable Long commentId,
                                @AuthenticationPrincipal UserDetails userDetails) {
        commentService.delete(commentId, userDetails.getUsername());
        return "redirect:/posts/" + postId;
    }
}
