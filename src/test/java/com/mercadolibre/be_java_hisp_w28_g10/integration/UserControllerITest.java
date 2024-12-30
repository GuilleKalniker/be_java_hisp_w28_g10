package com.mercadolibre.be_java_hisp_w28_g10.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.dto.follow.FollowersDTO;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;


import java.util.Set;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerITest {

    private UserRepositoryImpl userRepository;
    private User user;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        user = new User(1, "Test User");
        userRepository.getFollowRelationsByFollowerId(user.getId()); // Guarda el usuario en la base de datos
    }
    private static final FollowRelation followRelation1 = new FollowRelation(10, 1);
    private static final FollowRelation followRelation2 = new FollowRelation(11, 1);

    @Test
    void getAllUsers() {
    }

    @Test
    void getAllFollowRelations() {
    }

    @Test
    void addNewFollow() {
    }

    @Test
    void getAmountFollowersById() throws Exception {
        //ARRANGE
        int userId = 1;
        FollowRelation followEsperado = (FollowRelation) Set.of(followRelation1, followRelation2);
        ResultMatcher statusEsperado = status().isOk();
        ResultMatcher contentTypeEsperado = (ResultMatcher) content().contentType("application/json");
        ResultMatcher bodyEsperado = (ResultMatcher) content().json(objectMapper.writeValueAsString(followEsperado));

        // ACT AND ASSERT
        mockMvc.perform(get("users/{userId}/followers/count", userId))
                .andExpect(status().isOk())
                .andExpect(statusEsperado)
                .andExpect(contentTypeEsperado)
                .andExpect(bodyEsperado)
                .andDo(print());
    }

    @Test
    void unfollowUserById() {
    }

    @Test
    void getUserFollowersById() {
    }

    @Test
    void getUserFollowedById() {
    }
}