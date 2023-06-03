package com.example.demo.user.controller;

import com.example.demo.common.model.JsonReturnDTO;
import com.example.demo.common.model.PaginationDTO;
import com.example.demo.user.model.UserModel;
import com.example.demo.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping(path="/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonReturnDTO<PaginationDTO<UserModel>>> selectAll(
            @Valid @ModelAttribute final UserModel userModel
    ) throws Exception {

        return new ResponseEntity<>(JsonReturnDTO
                .<PaginationDTO<UserModel>>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.selectAll(userModel))
                .build(),
                HttpStatus.OK
        );
    }

    @PostMapping(path="/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonReturnDTO<Integer>> createOne(
            @Valid @RequestBody final UserModel userModel
    ) throws Exception {
        return new ResponseEntity<>(JsonReturnDTO
                .<Integer>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.insertOne(userModel))
                .build(),
                HttpStatus.OK
        );
    }

    @GetMapping(path="/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonReturnDTO<UserModel>> selectOne(
            @NotNull @PathVariable(name = "id") final Integer id
    ) throws  Exception {
        return new ResponseEntity<>(JsonReturnDTO
                .<UserModel>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.selectOne(id))
                .build(),
                HttpStatus.OK
        );
    }

    @PutMapping(path="/user/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonReturnDTO<Integer>> updateOne(
            @NotNull @PathVariable(name = "id") final Integer id,
            @Valid @RequestBody final UserModel userModel
    ) throws Exception {

        return new ResponseEntity<>(JsonReturnDTO
                .<Integer>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.updateOne(id, userModel))
                .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping(path="/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonReturnDTO<Integer>> deleteOne(
            @NotNull @PathVariable(name = "id") final Integer id
    ) throws  Exception {
        return new ResponseEntity<>(JsonReturnDTO
                .<Integer>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.deleteOne(id))
                .build(),
                HttpStatus.OK
        );
    }
}
