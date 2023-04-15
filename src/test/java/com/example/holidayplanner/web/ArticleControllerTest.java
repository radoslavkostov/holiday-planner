package com.example.holidayplanner.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void articlesTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/articles"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("articles"))
                .andExpect(view().name("/articles"));
    }

    @Test
    void detailsTest() throws Exception {
        Long articleId = 1L;
        mockMvc
                .perform(MockMvcRequestBuilders
                .get("/articles/"+articleId))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("article"))
            .andExpect(view().name("article-details"));

    }

}