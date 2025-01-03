package com.mercadolibre.be_java_hisp_w28_g10.integration;


import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ProductsWithPromoDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponseMessageDTO;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.DatosMock;
import com.mercadolibre.be_java_hisp_w28_g10.dto.post.PostDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.post.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @DisplayName("Should create a new post successfully and return it")
    void addPost_HappyPath() throws Exception {
        PostDTO postDTO = DatosMock.VALID_POST_DTO;

        // Act & Assert
        mockMvc.perform(post("/products/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.post_id").exists())
                .andExpect(jsonPath("$.user_id").value(postDTO.getId()))
                .andExpect(jsonPath("$.date").value(postDTO.getDate()))
                .andExpect(jsonPath("$.category").value(postDTO.getCategory()))
                .andExpect(jsonPath("$.price").value(postDTO.getPrice()))
                .andExpect(jsonPath("$.product.product_id").value(postDTO.getProduct().getId()));
    }

    @Test
    @DisplayName("Should return BadRequest when provided invalid data")
    void addPost_InvalidData() throws Exception {

        PostDTO invalidPostDTO = DatosMock.INVALID_POST_DTO;

        mockMvc.perform(post("/products/promo-post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPostDTO)))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.date").value("La fecha debe estar en el formato dd-MM-yyyy."),
                        jsonPath("$.price").value("El precio debe ser mayor a 0."),
                        jsonPath("$.id").value("El id debe ser mayor a cero."),
                        jsonPath("$.['product.id']").value("El id debe ser mayor a cero.")
                );
    }

    @DisplayName("Should create a new promotional post successfully and return it")
    @Test
    void addPromoPost() throws Exception {
        PostDTO promoPost = new PostDTO(5, 1, "15-12-2024", 100, 1500.50,
                new ProductDTO(1,
                        "Smartphone Galaxy S21",
                        "Electrónica",
                        "Samsung",
                        "Gris Fantasma",
                        "Smartphone 2024"),
                true,
                0.25);
        mockMvc.perform(post("/products/promo-post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(promoPost)))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.user_id").value(5),
                        jsonPath("$.category").value(100),
                        jsonPath("$.price").value(1500.50),
                        jsonPath("$.has_promo").value(true),
                        jsonPath("$.discount").value(0.25),
                        jsonPath("$.product.product_name").value("Smartphone Galaxy S21"),
                        jsonPath("$.product.type").value("Electrónica"),
                        jsonPath("$.product.brand").value("Samsung")
                );
    }

    @Test
    @DisplayName("Should return BadRequest when provided invalid data")
    void testAddPromoPost_InvalidData() throws Exception {

        ProductDTO product = new ProductDTO(-60, "Silla Gamer", "Gamer", "Racer", "Red & Black", "Special Edition");
        PostDTO invalidPromoPost = new PostDTO(-5, 100, "29-0-202011", 100, -1500.50, product, true, 0.25);

        mockMvc.perform(post("/products/promo-post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPromoPost)))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.date").value("La fecha debe estar en el formato dd-MM-yyyy."),
                        jsonPath("$.price").value("El precio debe ser mayor a 0."),
                        jsonPath("$.id").value("El id debe ser mayor a cero."),
                        jsonPath("$.['product.id']").value("El id debe ser mayor a cero.")
                );
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