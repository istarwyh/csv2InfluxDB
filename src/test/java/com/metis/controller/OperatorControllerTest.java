package com.metis.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
class OperatorControllerTest {
    @Autowired
    private WebApplicationContext webAppContext;

    /**
     * 使用MockMvc对象，我们可以向应用程序提供的地址发出请求，检查请求是否成功，并查看作为响应收到的数据。
     * https://materiaalit.github.io/wepa-s17/part4/
     */
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        log.info("----测试开始-----");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }
    @AfterEach
    public void tearDown() {
        log.info("----测试结束----");
    }
    @Test
    void greeting() throws Exception {
        mockMvc.perform(get("/operator/greeting")).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());;
    }

    @Test
    public void responseContainsText() throws Exception {
        MvcResult res = mockMvc.perform(get("/operator/greeting"))
                .andReturn();

        String content = res.getResponse().getContentAsString();
        log.info("content->{}",content);
        Assertions.assertTrue( content.contains("Hello  World"));
    }
}