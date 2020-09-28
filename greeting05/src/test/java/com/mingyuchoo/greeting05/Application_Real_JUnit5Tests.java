package com.mingyuchoo.greeting05;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        classes = {Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Application_Real_JUnit5Tests {

    @Autowired GreetingController greetingController;

    @Test
    void applicationContextLoads() {
        assertThat(greetingController).isNotNull();
    }
}
