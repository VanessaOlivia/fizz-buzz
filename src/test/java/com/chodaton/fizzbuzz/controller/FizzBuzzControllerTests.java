package com.chodaton.fizzbuzz.controller;

import com.chodaton.fizzbuzz.model.entity.TruncateRequestParams;
import com.chodaton.fizzbuzz.repository.FizzBuzzRepository;
import com.chodaton.fizzbuzz.service.FizzBuzzService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(properties = "spring.main.lazy-initialization=true")
 class FizzBuzzControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    FizzBuzzService fizzBuzzService;
    @Autowired
    FizzBuzzRepository repository;

    @Test
    void givenParams_whenGet_thenReturnStatusOKAndSaveRequest() throws Exception {
        this.mockMvc.perform(get("/fizz-buzz")
                        .contentType(APPLICATION_JSON)
                                .param("int1", "2")
                                .param("int2", "3")
                                .param("limit", "15")
                                .param("str1", "fizz")
                                .param("str2", "buzz")
                        )
                .andExpect(status().isOk())
                .andDo(print());
        Optional<TruncateRequestParams> savedParams = this.repository.findByInt1AndInt2AndLimitAndStr1AndStr2(2,3,15,"fizz","buzz");
        Assertions.assertTrue(savedParams.isPresent());
    }

    @Sql(value = "/insert-request-params.sql", executionPhase = BEFORE_TEST_METHOD)
    @Test
    void getMostFrequentRequestTest() throws Exception {
        this.mockMvc.perform(get("/fizz-buzz/frequent-request")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.url").value("/fizz-buzz?int1=5&int2=3&limit=13&str1=fizz&str2=buzz"))
                .andExpect(jsonPath("$.nbHits").value(15));

    }


}
