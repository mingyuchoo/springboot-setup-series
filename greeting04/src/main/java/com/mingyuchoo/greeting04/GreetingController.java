package com.mingyuchoo.greeting04;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class GreetingController {
    @Autowired private GreetingService service;

    @GetMapping("/")
    public @ResponseBody String home() {
        return "Welcome home!";
    }

    @GetMapping("/greeting")
    public @ResponseBody String greeting() {
        Person person = new Person();
        person.setName("ROBERTO");
        log.debug(">>>>> This is /greeting, " + person);
        return service.get();
    }
}
