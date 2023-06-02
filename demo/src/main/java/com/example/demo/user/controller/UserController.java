package com.example.demo.user.controller;

import com.example.demo.common.model.CommonReturnDTO;
import com.example.demo.user.model.UserVO;
import com.example.demo.user.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping(path="/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonReturnDTO<UserVO>> selectNameById(
            @NotNull @PathVariable (name = "userId") UserVO userVO
            ) throws Exception {

        return new ResponseEntity<>(CommonReturnDTO.<UserVO>builder()
                .successOrNot("Y")
                .statusCode("OK")
                .data(userService.selectNameById(userVO))
                .build(), HttpStatus.OK
        );
    }
}
