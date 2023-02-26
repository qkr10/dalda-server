package com.dalda.dalda_server.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = IndexController.class)
class IndexControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testIndex() throws Exception {

        String resultStr = "";

        mvc.perform(get("/"))
                .andExpect(content().string(resultStr));
    }

}