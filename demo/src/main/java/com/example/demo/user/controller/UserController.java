package com.example.demo.user.controller;

import com.example.demo.common.model.JsonReturnDTO;
import com.example.demo.common.model.PaginationDTO;
import com.example.demo.user.entity.User;
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
    public @ResponseBody JsonReturnDTO<PaginationDTO<User>> selectList(
            @Valid @ModelAttribute final User user
    ) throws Exception {

        return JsonReturnDTO.<PaginationDTO<User>>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.selectList(user))
                .build();
    }

    @PostMapping(path="/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JsonReturnDTO<Integer> insertItem(
            @Valid @RequestBody final User user
    ) throws Exception {
        return JsonReturnDTO
                .<Integer>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.insertItem(user))
                .build();
    }

    @GetMapping(path="/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JsonReturnDTO<User> selectItem(
            @NotNull @PathVariable(name = "id") final Integer id
    ) throws  Exception {
        return JsonReturnDTO
                .<User>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.selectItem(id))
                .build();
    }

    @PutMapping(path="/user/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JsonReturnDTO<Integer> updateItem(
            @NotNull @PathVariable(name = "id") final Integer id,
            @Valid @RequestBody final User user
    ) throws Exception {

        return JsonReturnDTO
                .<Integer>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.updateItem(id, user))
                .build();
    }

    @DeleteMapping(path="/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JsonReturnDTO<Integer> deleteItem(
            @NotNull @PathVariable(name = "id") final Integer id
    ) throws  Exception {
        return JsonReturnDTO
                .<Integer>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.deleteItem(id))
                .build();
    }
}
