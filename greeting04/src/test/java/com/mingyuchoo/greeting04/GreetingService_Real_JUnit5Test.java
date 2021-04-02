package com.mingyuchoo.greeting04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GreetingService_Real_JUnit5Test {

    @Autowired private GreetingService greetingService;

    @DisplayName("JUnit5 Test Integration test using @SpringBootTest and @Autowired for Service")
    @Test
    void it_should_be_return_valid_string() {
        assertEquals("Hello, World!", greetingService.get());
    }
}
