package com.mercadolibre.be_java_hisp_w28_g10.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.DatosMock;
import com.mercadolibre.be_java_hisp_w28_g10.dto.post.PostDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.post.ProductDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponsePostNoPromoDTO;
import com.mercadolibre.be_java_hisp_w28_g10.util.Utilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getAllProducts() {
    }

    @Test
    void addPost_HappyPath() throws Exception {
        PostDTO postDTO = DatosMock.VALID_POST_DTO;

        // Act & Assert
        mockMvc.perform(post("/products/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.post_id").exists())
                .andExpect(jsonPath("$.user_id").value(postDTO.getId()))
                .andExpect(jsonPath("$.date").value("19-12-2024"))
                .andExpect(jsonPath("$.category").value(postDTO.getCategory()))
                .andExpect(jsonPath("$.price").value(postDTO.getPrice()))
                .andExpect(jsonPath("$.product.product_id").value(postDTO.getProduct().getId()));
    }

    @Test
    void addPost_InvalidUserId() throws Exception {
        PostDTO postDTO = new PostDTO(0, 0, "19-12-2024", 100, 1500.50, DatosMock.VALID_PRODUCT_DTO, false, 0.0);

        // Act & Assert
        mockMvc.perform(post("/products/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").value("El id debe ser mayor a cero."));
    }

    @Test
    void addPost_InvalidPrice() throws Exception {
        PostDTO postDTO = new PostDTO(2, 0, "19-12-2024", 100, 0.0, DatosMock.VALID_PRODUCT_DTO, false, 0.0);

        mockMvc.perform(post("/products/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.price").value("El precio no puede ser 0."));
    }

    @Test
    void addPost_InvalidDateFormat() throws Exception {
        PostDTO postDTO = new PostDTO(2, 0, "2024-12-19", 100, 1500.50, DatosMock.VALID_PRODUCT_DTO, false, 0.0);

        mockMvc.perform(post("/products/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.date").value("La fecha debe estar en el formato dd-MM-yyyy."));
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
    void getLastFollowedPosts() {
    }
}