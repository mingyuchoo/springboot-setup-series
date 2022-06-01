package com.example.demo.controller;

import com.example.demo.entity.Coffee;
import com.example.demo.service.CoffeeService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * What is the test for?
 * Inner
 * Inter
 * Outer
 */

// @Disabled
@DisplayName("CoffeeController 에 GET 메소드로 호출 가능한 URI `/coffee` REST API 가 준비된 상황에서")
@WebMvcTest(CoffeeController.class)
class CoffeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoffeeService coffeeService;


    @DisplayName("/coffee/1 을 HTTP GET 메소드로 호출할 때, 이름이 \"브라질 산토스\"인 커피인 JSON 데이터를 받을 수 있어야 한다.")
    @Test
    void testFindById1() throws Exception {
        // Given: GET 메소드로 호출 가능한 URI `/coffee/1` REST API 가 준비된 상황에서
        when(coffeeService.findOneById(1)).thenReturn(new Coffee(1, "브라질 산토스"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/coffee/1");

        // When: /coffee/1 을 HTTP GET 메소드로 호출할 때
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        // Then: 이름이 "브라질 산토스"인 커피인 JSON 데이터를 받을 수 있어야 한다.
        Assertions.assertEquals("{\"id\":1,\"name\":\"브라질 산토스\"}", mvcResult.getResponse().getContentAsString());
    }

    @DisplayName("/coffee/2 를 HTTP GET 메소드로 호출할 때, 이름이 \"콜롬비아 수프리모\"인 커피인 JSON 데이터를 받을 수 있어야 한다.")
    @Test
    void testFindById2() throws Exception {
        // Given: GET 메소드로 호출 가능한 URI `/coffee/1` REST API 가 준비된 상황에서
        given(coffeeService.findOneById(1)).willReturn(new Coffee(1, "브라질 산토스"));

        // When: /coffee/1 을 HTTP GET 메소드로 호출할 때
        // Then: 이름이 "콜롬비아 수프리모"인 커피인 JSON 데이터를 받을 수 있어야 한다.
        mockMvc.perform(MockMvcRequestBuilders.get("/coffee/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name", containsString("콜롬비아 수프리모")))
                .andExpect(content().json("{\"id\":2,\"name\":\"콜롬비아 수프리모\"}"))
                .andDo(print());
    }
}