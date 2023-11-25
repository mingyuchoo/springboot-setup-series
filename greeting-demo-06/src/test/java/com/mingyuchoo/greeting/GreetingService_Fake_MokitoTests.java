package com.mingyuchoo.greeting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {GreetingRepository.class})
public class GreetingService_Fake_MokitoTests {

    @Mock private GreetingRepository greetingRepository;

    @InjectMocks private GreetingService greetingService = new GreetingServiceImpl();

    @BeforeEach
    void setSmokeOutput() {
        when(greetingRepository.get()).thenReturn("Hello Mockito From Repository");
    }

    @DisplayName("greetingService + the mocked greetingRepository")
    @Test
    void it_should_return_valid_string() {
        assertEquals("Hello Mockito From Repository", greetingService.get());
    }
}
