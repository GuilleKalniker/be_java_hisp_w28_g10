package com.mercadolibre.be_java_hisp_w28_g10.unit.impl;


import com.mercadolibre.be_java_hisp_w28_g10.dto.follow.FollowersDTO;
import com.mercadolibre.be_java_hisp_w28_g10.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.dto.follow.FollowRelationDTO;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IProductRepository;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.impl.UserServiceimpl;
import com.mercadolibre.be_java_hisp_w28_g10.util.Utilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceimplTest {
    @Mock
    private IProductRepository productRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private Utilities utilities;

    @InjectMocks
    private UserServiceimpl userService;
    private User user;
    private List<FollowRelation> followRelations;

    @Test
    void getAllUsers() {
    }

    @Test
    void getAllFollowRelation() {
    }

    @Test
    void unfollowUserById() {
    }

    @Test
    @DisplayName("Should allow an existing user following an existing user")
    void follow_verifyUsersExists_happyPathBothUsersExist() {

        //ARRANGE
        int followerId = 1;
        int followedId = 2;
        // Mock the repository methods
        when(userRepository.existsUser(followerId)).thenReturn(true);
        when(userRepository.existsUser(followedId)).thenReturn(true);
        when(userRepository.existsFollow(followerId, followedId)).thenReturn(false);

        // Mock the behavior of the utilities class
        FollowRelation mockFollowRelation = new FollowRelation(followerId, followedId);
        when(userRepository.saveFollow(followerId, followedId)).thenReturn(mockFollowRelation);
        when(utilities.convertValue(mockFollowRelation, FollowRelationDTO.class)).thenReturn(new FollowRelationDTO(followerId, followedId));

        //ACT
        FollowRelationDTO result = userService.follow(followerId, followedId);

        //ASSERT
        assertNotNull(result, "Result should not be null");
        assertEquals(followerId, result.getIdFollower(), "Follower ID should match");
        assertEquals(followedId, result.getIdFollowed(), "Followed ID should match");
    }

    @Test
    @DisplayName("Should throw an exception when trying to follow a non-existent user")
    void follow_followerUserDoesntExist_ThrowsException() {
        //ARRANGE
        int followerId = 1;
        int followedId = 999; // Non-existent user ID
        // Mock the repository behavior to simulate that the follower exists but the followed does not
        when(userRepository.existsUser(followerId)).thenReturn(true);
        when(userRepository.existsUser(followedId)).thenReturn(false);

        // ACT & ASSERT
        Assertions.assertThrows(NotFoundException.class, () -> userService.follow(followerId, followedId));
    }

    @Test
    @DisplayName("Should throw an exception when a non-existent user tries to follow another user")
    void follow_followedUserDoesntExist_ThrowsException() {
        //ARRANGE
        int followerId = 999; // Non-existent user ID
        int followedId = 1;
        // Mock the repository behavior to simulate that the followed exists but the follower does not
        when(userRepository.existsUser(followerId)).thenReturn(false);

        // ACT & ASSERT
        Assertions.assertThrows(NotFoundException.class, () -> userService.follow(followerId, followedId));
    }
    @Test
    void getUserFollowersAmountById_followersComplete_happyPath() {
        user = new User(2, "Pedro");
        //ARRANGE
        FollowRelation followRelation1 = new FollowRelation(10, user.getId());
        FollowRelation followRelation2 = new FollowRelation(1, user.getId());
        FollowRelation followRelation3 = new FollowRelation(11, user.getId());
        followRelations = Arrays.asList(followRelation1, followRelation2, followRelation3);
        when(userRepository.findUserById(1)).thenReturn(user);
        when(userRepository.findAllFollowRelation()).thenReturn(followRelations);
        //ACT
        FollowersDTO result = userService.getFollowersAmountById(1);
        //ASSERT
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(3, result.getFollowersCount());
    }
    @Test
    void testGetFollowersAmountById_UserNotFound_badPath() {
        // ARRANGE
        when(userRepository.findUserById(1)).thenReturn(null);
        // ACT
        assertThrows(NotFoundException.class, () -> userService.getFollowersAmountById(1));
    }

    @Test
    void getUserFollowedById() {
    }
}