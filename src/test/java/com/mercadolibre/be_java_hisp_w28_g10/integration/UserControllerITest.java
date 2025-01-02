package com.mercadolibre.be_java_hisp_w28_g10.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.dto.follow.FollowersDTO;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;


import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerITest {
    @Mock
    private UserRepositoryImpl userRepository;
    private User user;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1, "Alice");
        userRepository.getFollowRelationsByFollowerId(user.getId());
    }
    private static final FollowRelation followRelation1 = new FollowRelation(1, 10);

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
    void getAmountFollowersById_happyPath() throws Exception {
        // ARRANGE
        int userId = 1;
        List<FollowRelation> followEsperado = List.of(followRelation1);
        when(userRepository.getFollowRelationsByFollowerId(userId)).thenReturn(followEsperado);

        FollowersDTO expectedResponse = new FollowersDTO(userId, user.getName(), followEsperado.size());
        String expectedJson = objectMapper.writeValueAsString(expectedResponse);

        // ACT AND ASSERT
        mockMvc.perform(get("/users/{userId}/followers/count", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedJson))
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