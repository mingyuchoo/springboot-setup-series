package com.mingyuchoo.greeting04;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.BDDMockito.*;

/**
 * https://github.com/mockito/mockito/wiki/Mockito-features-in-Korean
 */
@SpringBootTest
public class GreetingRepository_Fake_BDDMokitoTest {

    @Mock
    private GreetingRepository greetingRepository;

    @Test
    void it_should_return_valid_string() {

        // given
        given(greetingRepository.get()).willReturn("Good Bye");

        // when
        when(greetingRepository.get())
                .thenReturn("Good Bye");
    }
}
