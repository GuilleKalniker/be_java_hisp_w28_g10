package com.mercadolibre.be_java_hisp_w28_g10.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerITest {
    @Autowired
    private UserRepositoryImpl userRepository;
    private User user;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        user = new User(1, "Test User");
        userRepository.getFollowRelationsByFollowerId(user.getId());
    }
    private static final FollowRelation followRelation1 = new FollowRelation(1, 10);
    private static final FollowRelation followRelation2 = new FollowRelation(1, 11);

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