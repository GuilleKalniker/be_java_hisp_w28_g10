package com.mercadolibre.be_java_hisp_w28_g10.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.dto.follow.FollowRelationDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponseMessageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerITest {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void getAllFollowRelations() {
    }

    @Test
    public void addNewFollow_HappyPath() throws Exception {
        // ARRANGE
        FollowRelationDTO expectedFollow = new FollowRelationDTO(1, 5);
        String expectedFollowJSON = objectMapper.writeValueAsString(expectedFollow);

        // ACT AND ASSERT
        mockMvc.perform(post("/users/{userId}/follow/{userIdToFollow}", 1, 5))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedFollowJSON))
                .andDo(print());
    }

    @Test
    public void addNewFollow_UserDoesNotExist() throws Exception {
        //ARRANGE
        ResponseMessageDTO expectedResponseMessage = new ResponseMessageDTO("Invalid UserId ");
        String responseMessageJSON = objectMapper.writeValueAsString(expectedResponseMessage);

        // ACT AND ASSERT
        mockMvc.perform(post("/users/{userId}/follow/{userIdToFollow}", 999, 2)) // Invalid userId
                .andExpect(status().isNotFound())
                .andExpect(content().json(responseMessageJSON))
                .andDo(print());
    }

    @Test
    public void addNewFollow_UserToFollowDoesNotExist() throws Exception {
        //ARRANGE
        ResponseMessageDTO expectedResponseMessage = new ResponseMessageDTO("Invalid userIdToFollow");
        String responseMessageJSON = objectMapper.writeValueAsString(expectedResponseMessage);

        // ACT AND ASSERT
        mockMvc.perform(post("/users/{userId}/follow/{userIdToFollow}", 1, 999)) // Invalid userIdToFollow
                .andExpect(status().isNotFound())
                .andExpect(content().json(responseMessageJSON))
                .andDo(print());
    }

    @Test
    public void addNewFollow_UserFollowsThemselves() throws Exception {
        //ARRANGE
        ResponseMessageDTO expectedResponseMessage = new ResponseMessageDTO("The user cannot follow itself");
        String responseMessageJSON = objectMapper.writeValueAsString(expectedResponseMessage);

        // ACT AND ASSERT
        mockMvc.perform(post("/users/{userId}/follow/{userIdToFollow}", 1, 1)) // user tries to follow themselves
                .andExpect(status().isConflict())
                .andExpect(content().json(responseMessageJSON))
                .andDo(print());
    }

    @Test
    public void addNewFollow_AlreadyFollowingUser() throws Exception {
        //ARRANGE
        ResponseMessageDTO expectedResponseMessage = new ResponseMessageDTO("The follow already exists");
        String responseMessageJSON = objectMapper.writeValueAsString(expectedResponseMessage);

        // Act and Assert
        mockMvc.perform(post("/users/{userId}/follow/{userIdToFollow}", 1, 2)) // User already following user 2
                .andExpect(status().isConflict())
                .andExpect(content().json(responseMessageJSON))
                .andDo(print());
    }

    @Test
    void getAmountFollowersById() throws Exception {

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