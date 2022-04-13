package com.mingyuchoo.h2demo;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MyController.class) // USE FOR UNIT TESTING OF CONTROLLER LAYER
public class MyController_WebMvcTest_UnitTests {

    @Autowired private MockMvc mockMvc;

    // USE FOR UNIT TESTING OF CONTROLLER LAYER
    @MockBean private MyService myService;
    @MockBean private MyRepository myRepository; // IMPORTANT

    @Test
    public void home() throws Exception {
        mockMvc.perform(get("/api/v1/").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsStringIgnoringCase("home")));
    }

    @Test
    public void hello() throws Exception {
        // USE FOR UNIT TESTING OF CONTROLLER LAYER
        when(myService.get()).thenReturn(new MyEntity("Hello", "World"));

        mockMvc.perform(get("/api/v1/hello").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsStringIgnoringCase("hello")));
    }
}
