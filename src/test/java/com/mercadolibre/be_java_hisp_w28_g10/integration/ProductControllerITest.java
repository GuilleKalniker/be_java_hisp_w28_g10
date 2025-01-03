package com.mercadolibre.be_java_hisp_w28_g10.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.DatosMock;
import com.mercadolibre.be_java_hisp_w28_g10.dto.follow.ResponseFollowedPostsDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponseMessageDTO;
import org.apache.commons.collections4.Get;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductControllerITest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {

    }

    @Test
    void getAllProducts() {
    }

    @Test
    void addPost() {
    }

    @Test
    void addPromoPost() {
    }

    @Test
    void getAllPost() {
    }

    @Test
    void getPromoProductCountByUserId() {
    }

    @Test
    void getLastFollowedPosts_ascHappyPath() throws Exception{
        //ARRANGE
        ResponseFollowedPostsDTO expectedResponseMessage = new ResponseFollowedPostsDTO(2, DatosMock.TEST_POST_LIST_ASC.reversed());
        String responseMessageJSON = objectMapper.writeValueAsString(expectedResponseMessage);
        //ACT AND ASSERT
        mockMvc.perform(get("/products/followed/{userId}/list", 2).param("order", "date_asc"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseMessageJSON))
                .andDo(print());
    }

    @Test
    void getLastFollowedPosts_descHappyPath() throws Exception {
        //ARRANGE
        ResponseFollowedPostsDTO expectedResponseMessage = new ResponseFollowedPostsDTO(2, DatosMock.TEST_POST_LIST_ASC);
        String responseMessageJSON = objectMapper.writeValueAsString(expectedResponseMessage);
        //ACT AND ASSERT
        mockMvc.perform(get("/products/followed/{userId}/list", 2).param("order", "date_desc"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseMessageJSON))
                .andDo(print());
    }

    @Test
    void getLastFollowedPosts_invalidOrder() throws Exception {
        //ARRANGE
        ResponseMessageDTO expectedResponseMessage = new ResponseMessageDTO("That's not a valid order criteria: date_asc_falla");
        String responseMessageJSON = objectMapper.writeValueAsString(expectedResponseMessage);

        //ACT AND ASSERT
        mockMvc.perform(get("/products/followed/{userId}/list", 2).param("order", "date_asc_falla")) // Invalid userId
                .andExpect(status().isBadRequest())
                .andExpect(content().json(responseMessageJSON))
                .andDo(print());
    }

    @Test
    void getLastFollowedPosts_invalidUser() throws Exception {
        //ARRANGE
        ResponseMessageDTO expectedResponseMessage = new ResponseMessageDTO("User not found");
        String responseMessageJSON = objectMapper.writeValueAsString(expectedResponseMessage);

        //ACT AND ASSERT
        mockMvc.perform(get("/products/followed/{userId}/list", 999).param("order", "date_asc")) // Invalid userId
                .andExpect(status().isNotFound())
                .andExpect(content().json(responseMessageJSON))
                .andDo(print());
    }
}