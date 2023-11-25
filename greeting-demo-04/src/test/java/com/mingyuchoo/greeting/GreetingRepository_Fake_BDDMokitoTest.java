package com.mingyuchoo.greeting;

import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

/** https://github.com/mockito/mockito/wiki/Mockito-features-in-Korean */
@SpringBootTest
public class GreetingRepository_Fake_BDDMokitoTest {

    @Mock private GreetingRepository greetingRepository;

    @Test
    void it_should_return_valid_string() {

        // given
        given(greetingRepository.get()).willReturn("Good Bye");

        // when
        when(greetingRepository.get()).thenReturn("Good Bye");
    }
}
