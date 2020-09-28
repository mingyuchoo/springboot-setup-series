package com.mingyuchoo.greeting04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {GreetingService.class})
public class GreetingController_Fake_MockitoTest {

    @Mock
    private GreetingService greetingService;

    @InjectMocks
    private GreetingController greetingController = new GreetingController();

    @BeforeEach
    void setSmokeOutput() {
        when(greetingService.get()).thenReturn("Hello, Robert!");
    }

    @DisplayName("greetingController + mocked greetingService")
    @Test
    void it_should_return_valid_string() {
        assertEquals("Hello, Robert!", greetingController.greeting());
    }
}
