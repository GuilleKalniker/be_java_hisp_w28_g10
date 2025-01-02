package com.mercadolibre.be_java_hisp_w28_g10.unit.impl;

import com.mercadolibre.be_java_hisp_w28_g10.DatosMock;
import com.mercadolibre.be_java_hisp_w28_g10.dto.follow.UserFollowedDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.follow.UserFollowersDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponseUserDTO;
import com.mercadolibre.be_java_hisp_w28_g10.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w28_g10.dto.follow.FollowRelationDTO;
import com.mercadolibre.be_java_hisp_w28_g10.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
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

    @Test
    @DisplayName("Should return a valid UserFollowersDTO with a list of ResponseUserDTO ordered by name ascendant")
    void getUserFollowersById_orderedResponseUserDTOsByName_nameAscHappyPath() {
        // Arrange & Act
        String order = "name_asc";
        List<ResponseUserDTO> responseUserDTOs = getMockedUserFollowersById(order).getFollowers();

        // Assert
        assertEquals("Eve", responseUserDTOs.get(0).getName());
        assertEquals("Grace", responseUserDTOs.get(1).getName());
        assertEquals("Pepe", responseUserDTOs.get(2).getName());
        assertEquals("Ron", responseUserDTOs.get(3).getName());
    }

    @Test
    @DisplayName("Should return a valid UserFollowersDTO with a list of ResponseUserDTO ordered by name descendant")
    void getUserFollowersById_orderedResponseUserDTOsByName_nameDescHappyPath() {
        // Arrange & Act
        String order = "name_desc";
        List<ResponseUserDTO> responseUserDTOs = getMockedUserFollowersById(order).getFollowers();

        // Assert
        assertEquals("Ron", responseUserDTOs.get(0).getName());
        assertEquals("Pepe", responseUserDTOs.get(1).getName());
        assertEquals("Grace", responseUserDTOs.get(2).getName());
        assertEquals("Eve", responseUserDTOs.get(3).getName());
    }

    @Test
    @DisplayName("Should return a valid UserFollowersDTO with a list of ResponseUserDTO ordered by name ascendant")
    void getUserFollowedById_orderedResponseUserDTOsByName_nameAscHappyPath() {
        // Arrange & Act
        String order = "name_asc";
        List<ResponseUserDTO> responseUserDTOs = getMockedUserFollowedById(order).getFollowed();

        // Assert
        assertEquals("Eve", responseUserDTOs.get(0).getName());
        assertEquals("Grace", responseUserDTOs.get(1).getName());
        assertEquals("Pepe", responseUserDTOs.get(2).getName());
        assertEquals("Ron", responseUserDTOs.get(3).getName());
    }

    @Test
    @DisplayName("Should return a valid UserFollowedDTO with a list of ResponseUserDTO ordered by name descendant")
    void getUserFollowedById_orderedResponseUserDTOsByName_nameDescHappyPath() {
        // Arrange & Act
        String order = "name_desc";
        List<ResponseUserDTO> responseUserDTOs = getMockedUserFollowedById(order).getFollowed();

        // Assert
        assertEquals("Ron", responseUserDTOs.get(0).getName());
        assertEquals("Pepe", responseUserDTOs.get(1).getName());
        assertEquals("Grace", responseUserDTOs.get(2).getName());
        assertEquals("Eve", responseUserDTOs.get(3).getName());
    }

    private UserFollowedDTO getMockedUserFollowedById(String order) {
        when(userRepository.findUserById(anyInt())).thenReturn(DatosMock.USER_1);
        when(userRepository.getFollowRelationsByFollowerId(anyInt()))
                .thenReturn(DatosMock.FOLLOW_RELATIONS_4);
        when(userRepository.getUserById(DatosMock.USER_2.getId())).thenReturn(DatosMock.USER_2);
        when(userRepository.getUserById(DatosMock.USER_3.getId())).thenReturn(DatosMock.USER_3);
        when(userRepository.getUserById(DatosMock.USER_5.getId())).thenReturn(DatosMock.USER_5);
        when(userRepository.getUserById(DatosMock.USER_7.getId())).thenReturn(DatosMock.USER_7);

        return userService.getUserFollowedById(DatosMock.USER_1.getId(), order);
    }

    private UserFollowersDTO getMockedUserFollowersById(String order) {
        when(userRepository.findUserById(anyInt())).thenReturn(DatosMock.USER_1);
        when(userRepository.getFollowRelationsByFollowedId(anyInt()))
                .thenReturn(DatosMock.FOLLOW_RELATIONS_3);
        when(userRepository.getUserById(DatosMock.USER_2.getId())).thenReturn(DatosMock.USER_2);
        when(userRepository.getUserById(DatosMock.USER_3.getId())).thenReturn(DatosMock.USER_3);
        when(userRepository.getUserById(DatosMock.USER_5.getId())).thenReturn(DatosMock.USER_5);
        when(userRepository.getUserById(DatosMock.USER_7.getId())).thenReturn(DatosMock.USER_7);

        return userService.getUserFollowersById(DatosMock.USER_1.getId(), order);
    }

    @Test
    void getUserFollowedById() {
    }
}