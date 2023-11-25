package com.mingyuchoo.greeting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class GreetingController {
    @Autowired private GreetingService service;

    @GetMapping(
            value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody String home() {
        return "Welcome home!";
    }

    @GetMapping(
            value = "/greeting",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody String greeting() {
        return service.get();
    }
}
