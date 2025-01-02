package com.mercadolibre.be_java_hisp_w28_g10.unit.impl;

import com.mercadolibre.be_java_hisp_w28_g10.DatosMock;
import com.mercadolibre.be_java_hisp_w28_g10.dto.follow.UserFollowedDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.follow.UserFollowersDTO;
import com.mercadolibre.be_java_hisp_w28_g10.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w28_g10.dto.follow.FollowRelationDTO;
import com.mercadolibre.be_java_hisp_w28_g10.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.DatosMock;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponseMessageDTO;
import com.mercadolibre.be_java_hisp_w28_g10.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w28_g10.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IProductRepository;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.impl.UserServiceimpl;
import com.mercadolibre.be_java_hisp_w28_g10.util.Utilities;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
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

    @Test
    void getAllUsers() {
    }

    @Test
    void getAllFollowRelation() {
    }

    @Test
    @DisplayName("Should unfollow a user successfully when follow relation exists")
    void unfollowUserById_validFollowRelation_happyPath() {
        // Arrange
        int userId = DatosMock.USER_1.getId();
        int userIdToUnfollow = DatosMock.USER_2.getId();

        when(userRepository.findAllFollowRelation()).thenReturn(DatosMock.FOLLOW_RELATIONS);
        when(userRepository.deleteFollowRelation(DatosMock.FOLLOW_RELATION)).thenReturn(true);

        // Act
        ResponseMessageDTO response = userService.unfollowUserById(userId, userIdToUnfollow);

        // Assert
        verify(userRepository).deleteFollowRelation(DatosMock.FOLLOW_RELATION);
        assertNotNull(response);
        assertEquals("The user with id:2 was unfollowed successfully.", response.getMessage());
    }

    @Test
    @DisplayName("Should throw NotFoundException when attempting to unfollow a user that does not exist")
    void unfollowUserById_nonExistentUser_sadPath() {
        // Arrange
        int userId = DatosMock.USER_1.getId();
        int userIdToUnfollow = 999;

        when(userRepository.findAllFollowRelation()).thenReturn(DatosMock.FOLLOW_RELATIONS);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userService.unfollowUserById(userId, userIdToUnfollow);
        });
        assertEquals("No follower relationship found for the given ids", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw BadRequestException when unable to delete follow relation")
    void unfollowUserById_couldNotDeleteFollowRelation_sadPath() {
        // Arrange
        int userId = DatosMock.USER_1.getId();
        int userIdToUnfollow = DatosMock.USER_2.getId();

        when(userRepository.findAllFollowRelation()).thenReturn(DatosMock.FOLLOW_RELATIONS);
        when(userRepository.deleteFollowRelation(DatosMock.FOLLOW_RELATION)).thenReturn(false);

        // Act & Assert
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.unfollowUserById(userId, userIdToUnfollow);
        });
        assertEquals("Couldn´t delete the follow relation", exception.getMessage());
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
    void getFollowersAmountById() {
    }

    @Test
    @DisplayName("Should return a valid UserFollowersDTO as 'name_asc' is a valid order")
    void getUserFollowersById_validOrderRequestParam_nameAscHappyPath() {
        // Arrange & Act
        String order = "name_asc";
        UserFollowersDTO userFollowersDTO = getMockedUserFollowersById(order);

        // Assert
        assertNotNull(userFollowersDTO);
        Assertions.assertFalse(userFollowersDTO.getFollowers().isEmpty());
    }

    @Test
    @DisplayName("Should return a valid UserFollowersDTO as 'name_desc' is a valid order")
    void getUserFollowersById_validOrderRequestParam_nameDescHappyPath() {
        // Arrange & Act
        String order = "name_desc";
        UserFollowersDTO userFollowersDTO = getMockedUserFollowersById(order);

        // Assert
        assertNotNull(userFollowersDTO);
        Assertions.assertFalse(userFollowersDTO.getFollowers().isEmpty());
    }

    @Test
    @DisplayName("Should return a valid UserFollowersDTO as '' is a valid order")
    void getUserFollowersById_validOrderRequestParam_emptyHappyPath() {
        // Arrange & Act
        String order = "";
        UserFollowersDTO userFollowersDTO = getMockedUserFollowersById(order);

        // Assert
        assertNotNull(userFollowersDTO);
        Assertions.assertFalse(userFollowersDTO.getFollowers().isEmpty());
    }

    @Test
    @DisplayName("Should throw an exception as 'unexistingOrder' is not a valid order")
    void getUserFollowersById_validOrderRequestParam_sadPath() {
        // Arrange & Act & Assert
        String order = "unexistingOrder";

        Assertions.assertThrows(BadRequestException.class, () -> getMockedUserFollowersById(order));
    }

    @Test
    @DisplayName("Should return a valid UserFollowedDTO as 'name_asc' is a valid order")
    void getUserFollowedById_validOrderRequestParam_nameAscHappyPath() {
        // Arrange & Act
        String order = "name_asc";
        UserFollowedDTO userFollowedDTO = getMockedUserFollowedById(order);

        // Assert
        assertNotNull(userFollowedDTO);
        Assertions.assertFalse(userFollowedDTO.getFollowed().isEmpty());
    }

    @Test
    @DisplayName("Should return a valid UserFollowedDTO as 'name_desc' is a valid order")
    void getUserFollowedById_validOrderRequestParam_nameDescHappyPath() {
        // Arrange & Act
        String order = "name_desc";
        UserFollowedDTO userFollowedDTO = getMockedUserFollowedById(order);

        // Assert
        assertNotNull(userFollowedDTO);
        Assertions.assertFalse(userFollowedDTO.getFollowed().isEmpty());
    }

    @Test
    @DisplayName("Should return a valid UserFollowedDTO as '' is a valid order")
    void getUserFollowedById_validOrderRequestParam_emptyHappyPath() {
        // Arrange & Act
        String order = "";
        UserFollowedDTO userFollowedDTO = getMockedUserFollowedById(order);

        // Assert
        assertNotNull(userFollowedDTO);
        Assertions.assertFalse(userFollowedDTO.getFollowed().isEmpty());
    }

    @Test
    @DisplayName("Should throw an exception as 'unexistingOrder' is not a valid order")
    void getUserFollowedById_validOrderRequestParam_sadPath() {
        // Arrange & act
        String order = "unexistingOrder";

        Assertions.assertThrows(BadRequestException.class, () -> getMockedUserFollowedById(order));
    }

    private UserFollowedDTO getMockedUserFollowedById(String order) {
        when(userRepository.findUserById(anyInt())).thenReturn(DatosMock.USER_1);
        when(userRepository.getFollowRelationsByFollowerId(anyInt()))
                .thenReturn(DatosMock.FOLLOW_RELATIONS_4);
        when(userRepository.getUserById(DatosMock.USER_2.getId())).thenReturn(DatosMock.USER_2);
        when(userRepository.getUserById(DatosMock.USER_3.getId())).thenReturn(DatosMock.USER_3);

        return userService.getUserFollowedById(DatosMock.USER_1.getId(), order);
    }

    private UserFollowersDTO getMockedUserFollowersById(String order) {
        when(userRepository.findUserById(anyInt())).thenReturn(DatosMock.USER_1);
        when(userRepository.getFollowRelationsByFollowedId(anyInt()))
                .thenReturn(DatosMock.FOLLOW_RELATIONS_3);
        when(userRepository.getUserById(DatosMock.USER_2.getId())).thenReturn(DatosMock.USER_2);
        when(userRepository.getUserById(DatosMock.USER_3.getId())).thenReturn(DatosMock.USER_3);

        return userService.getUserFollowersById(DatosMock.USER_1.getId(), order);
    }

    @Test
    void getUserFollowedById() {
    }
}