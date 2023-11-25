package com.mingyuchoo.web.user.controller;

import com.mingyuchoo.web.common.response.JsonResponse;
import com.mingyuchoo.web.common.response.PageResponse;
import com.mingyuchoo.web.user.model.User;
import com.mingyuchoo.web.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping(path="/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JsonResponse<Integer> insertUserItem(
            @Valid @RequestBody final User user
    ) throws Exception {
        return JsonResponse
                .<Integer>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.insertUserItem(user))
                .build();
    }

    @GetMapping(path="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JsonResponse<PageResponse<User>> selectUserList(
            @Valid @ModelAttribute final User user
    ) throws Exception {
        return JsonResponse.<PageResponse<User>>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.selectUserList(user))
                .build();
    }

    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JsonResponse<User> selectUserItem(
            @NotNull @PathVariable(name = "id") final Integer id
    ) throws  Exception {
        return JsonResponse
                .<User>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.selectUserItem(id))
                .build();
    }

    @GetMapping(path = "/{id}/blogs", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JsonResponse<User> selectUserItemWithBlogList(
            @NotNull @PathVariable(name="id") final Integer id
    ) throws Exception {

        User searchUser = User.builder().id(id).build();

        return JsonResponse.<User>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.selectUserItemWithBlogList(searchUser))
                .build();
    }

    @PutMapping(path="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JsonResponse<Integer> updateUserItem(
            @NotNull @PathVariable(name = "id") final Integer id,
            @Valid @RequestBody final User user
    ) throws Exception {

        return JsonResponse
                .<Integer>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.updateUserItem(id, user))
                .build();
    }

    @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JsonResponse<Integer> deleteUserItem(
            @NotNull @PathVariable(name = "id") final Integer id
    ) throws  Exception {
        return JsonResponse
                .<Integer>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.deleteUserItem(id))
                .build();
    }
}
