package com.mercadolibre.be_java_hisp_w28_g10.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.DatosMock;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ProductsWithPromoDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponseMessageDTO;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductControllerITest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
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
    void getPromoProductCountByUserId_happyPathCorrectAmount() throws Exception {
        //Arrange
        User user = new User(DatosMock.USER_1.getId(), DatosMock.USER_1.getName());
        ProductsWithPromoDTO expectedDto = new ProductsWithPromoDTO(user.getId(), user.getName(), DatosMock.POST_LIST1.size());
        String expectedBody = objectMapper.writeValueAsString(expectedDto);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products/promo-post/count")
                        .param("user_id", String.valueOf(user.getId())))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedBody))
                .andDo(print());
    }

    @Test
    void getPromoProductCountByUserId_sadPathUserNotFound() throws Exception{
        // Arrrange
        ResponseMessageDTO expectedResponseMessage = new ResponseMessageDTO("User not found");
        String responseMessageJSON = objectMapper.writeValueAsString(expectedResponseMessage);

        // Act and Assert
        mockMvc.perform(get("/products/promo-post/count")
                        .param("user_id","20"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(responseMessageJSON))
                .andDo(print());
    }

    @Test
    void getLastFollowedPosts() {
    }
}