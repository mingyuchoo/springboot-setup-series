package com.mingyuchoo.h2demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class MyController {
    @Autowired private MyService service;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public String home() {
        return "Welcome home!";
    }

    @GetMapping(
            value = "/hello",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MyResponseModel hello() {
        MyEntity myEntity = service.get();
        MyResponseModel myResponseModel = new MyResponseModel();
        myResponseModel.setMessage(myEntity.toString());
        myResponseModel.setSuccess(Boolean.TRUE);
        return myResponseModel;
    }
}
