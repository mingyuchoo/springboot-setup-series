package com.example.demo.user.controller;

import com.example.demo.common.model.JsonReturnDTO;
import com.example.demo.common.model.PaginationDTO;
import com.example.demo.user.model.User;
import com.example.demo.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping(path="/user/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
        public  @ResponseBody Iterable<User> findAll() throws Exception {

        return userService.findAll();
    }

    @GetMapping(path="/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JsonReturnDTO<PaginationDTO<User>> selectUserList(
            @Valid @ModelAttribute final User user
    ) throws Exception {

        return JsonReturnDTO.<PaginationDTO<User>>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.selectUserList(user))
                .build();
    }

    @GetMapping(path="/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JsonReturnDTO<User> selectUserItem(
            @NotNull @PathVariable(name = "id") final Integer id
    ) throws  Exception {
        return JsonReturnDTO
                .<User>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.selectUserItem(id))
                .build();
    }

    @GetMapping(path = "/user/{id}/blogs", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JsonReturnDTO<User> selectUserItemWithBlogList(
            @NotNull @PathVariable(name="id") final Integer id
    ) throws Exception {

        User searchUser = User.builder().id(id).build();

        return JsonReturnDTO.<User>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.selectUserItemWithBlogList(searchUser))
                .build();
    }

    @PostMapping(path="/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JsonReturnDTO<Integer> insertUserItem(
            @Valid @RequestBody final User user
    ) throws Exception {
        return JsonReturnDTO
                .<Integer>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.insertUserItem(user))
                .build();
    }

    @PutMapping(path="/user/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JsonReturnDTO<Integer> updateUserItem(
            @NotNull @PathVariable(name = "id") final Integer id,
            @Valid @RequestBody final User user
    ) throws Exception {

        return JsonReturnDTO
                .<Integer>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.updateUserItem(id, user))
                .build();
    }

    @DeleteMapping(path="/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JsonReturnDTO<Integer> deleteUserItem(
            @NotNull @PathVariable(name = "id") final Integer id
    ) throws  Exception {
        return JsonReturnDTO
                .<Integer>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.deleteUserItem(id))
                .build();
    }
}
