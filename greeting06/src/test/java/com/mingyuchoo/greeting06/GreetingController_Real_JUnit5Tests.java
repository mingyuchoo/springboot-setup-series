package com.mingyuchoo.greeting06;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingController_Real_JUnit5Tests {
    @LocalServerPort private int port;

    @Autowired private TestRestTemplate testRestTemplate;

    @Test
    public void getGreeting() throws Exception {
        ResponseEntity<String> response =
                testRestTemplate.getForEntity(
                        new URL("http://localhost:" + port + "/").toString(), String.class);
        assertEquals("Welcome home!", response.getBody());
    }
}
