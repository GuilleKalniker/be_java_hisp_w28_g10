package com.mercadolibre.be_java_hisp_w28_g10.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.dto.post.PostDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.post.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
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
    @DisplayName("Should create a new promotional post successfully")
    void addPost() {
    }

    @Test
    void addPromoPost() throws Exception {
        PostDTO promoPost = new PostDTO(5, 1, "15-12-2024", 100, 1500.50,
                new ProductDTO(1,
                        "Smartphone Galaxy S21",
                        "Electrónica",
                        "Samsung",
                        "Gris Fantasma",
                        "Smartphone2024"),
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
                        jsonPath("$.price").value("El precio no puede ser 0."),
                        jsonPath("$.id").value("El id debe ser mayor a cero."),
                        jsonPath("$.['product.id']").value("El id debe ser mayor a cero.")
                );
    }

    @Test
    void getAllPost() {
    }

    @Test
    void getPromoProductCountByUserId() {
    }

    @Test
    void getLastFollowedPosts() {
    }
}