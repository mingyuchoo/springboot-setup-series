package com.mingyuchoo.greeting;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(GreetingController.class)
public class GreetingController_Fake_WebMvcTests {

    @Autowired MockMvc mockMvc;

    @MockBean private GreetingService greetingService;

    @Test
    void getGreeting() throws Exception {
        // given
        given(greetingService.get()).willReturn("Hello, World!");

        // when
        final ResultActions resultActions =
                mockMvc.perform(get("/greeting").contentType(MediaType.APPLICATION_JSON))
                        .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}
