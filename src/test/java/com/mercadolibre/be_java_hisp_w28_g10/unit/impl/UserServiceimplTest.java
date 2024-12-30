package com.mercadolibre.be_java_hisp_w28_g10.unit.impl;

import com.mercadolibre.be_java_hisp_w28_g10.dto.follow.UserFollowedDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.follow.UserFollowersDTO;
import com.mercadolibre.be_java_hisp_w28_g10.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w28_g10.dto.follow.FollowRelationDTO;
import com.mercadolibre.be_java_hisp_w28_g10.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
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


    private final User mockedUser1 = new User(1, "Lorena");
    private final User mockedUser2 = new User(2, "Pepe");
    private final User mockedUser3 = new User(3, "Juan");

    private final List<FollowRelation> mockedFollowerRelations = List.of(
            new FollowRelation(2, 1),
            new FollowRelation(3, 1)
    );

    private final List<FollowRelation> mockedFollowedRelations = List.of(
            new FollowRelation(1, 2),
            new FollowRelation(1, 3)
    );


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
        // Arrange
        String order = "name_asc";

        // Act
        when(userRepository.findUserById(anyInt())).thenReturn(mockedUser1);
        when(userRepository.getFollowRelationsByFollowedId(anyInt()))
                .thenReturn(mockedFollowerRelations);
        when(userRepository.getUserById(mockedUser2.getId())).thenReturn(mockedUser2);
        when(userRepository.getUserById(mockedUser3.getId())).thenReturn(mockedUser3);

        UserFollowersDTO userFollowersDTO = userService.getUserFollowersById(mockedUser1.getId(), order);

        // Assert
        assertNotNull(userFollowersDTO);
        Assertions.assertFalse(userFollowersDTO.getFollowers().isEmpty());
    }

    @Test
    @DisplayName("Should return a valid UserFollowersDTO as 'name_desc' is a valid order")
    void getUserFollowersById_validOrderRequestParam_nameDescHappyPath() {
        // Arrange
        String order = "name_desc";

        // Act
        when(userRepository.findUserById(anyInt())).thenReturn(mockedUser1);
        when(userRepository.getFollowRelationsByFollowedId(anyInt()))
                .thenReturn(mockedFollowerRelations);
        when(userRepository.getUserById(mockedUser2.getId())).thenReturn(mockedUser2);
        when(userRepository.getUserById(mockedUser3.getId())).thenReturn(mockedUser3);

        UserFollowersDTO userFollowersDTO = userService.getUserFollowersById(mockedUser1.getId(), order);

        // Assert
        assertNotNull(userFollowersDTO);
        Assertions.assertFalse(userFollowersDTO.getFollowers().isEmpty());
    }

    @Test
    @DisplayName("Should return a valid UserFollowersDTO as '' is a valid order")
    void getUserFollowersById_validOrderRequestParam_emptyHappyPath() {
        // Arrange
        String order = "";

        // Act
        when(userRepository.findUserById(anyInt())).thenReturn(mockedUser1);
        when(userRepository.getFollowRelationsByFollowedId(anyInt()))
                .thenReturn(mockedFollowerRelations);
        when(userRepository.getUserById(mockedUser2.getId())).thenReturn(mockedUser2);
        when(userRepository.getUserById(mockedUser3.getId())).thenReturn(mockedUser3);

        UserFollowersDTO userFollowersDTO = userService.getUserFollowersById(mockedUser1.getId(), order);

        // Assert
        assertNotNull(userFollowersDTO);
        Assertions.assertFalse(userFollowersDTO.getFollowers().isEmpty());
    }

    @Test
    @DisplayName("Should throw an exception as 'unexistingOrder' is not a valid order")
    void getUserFollowersById_validOrderRequestParam_sadPath() {
        // Arrange
        String order = "unexistingOrder";

        // Act & Assert
        when(userRepository.findUserById(anyInt())).thenReturn(mockedUser1);
        when(userRepository.getFollowRelationsByFollowedId(anyInt()))
                .thenReturn(mockedFollowerRelations);
        when(userRepository.getUserById(mockedUser2.getId())).thenReturn(mockedUser2);
        when(userRepository.getUserById(mockedUser3.getId())).thenReturn(mockedUser3);

        Assertions.assertThrows(BadRequestException.class, () -> userService.getUserFollowersById(mockedUser1.getId(), order));
    }

    @Test
    @DisplayName("Should return a valid UserFollowedDTO as 'name_asc' is a valid order")
    void getUserFollowedById_validOrderRequestParam_nameAscHappyPath() {
        // Arrange
        String order = "name_asc";

        // Act
        when(userRepository.findUserById(anyInt())).thenReturn(mockedUser1);
        when(userRepository.getFollowRelationsByFollowerId(anyInt()))
                .thenReturn(mockedFollowedRelations);
        when(userRepository.getUserById(mockedUser2.getId())).thenReturn(mockedUser2);
        when(userRepository.getUserById(mockedUser3.getId())).thenReturn(mockedUser3);

        UserFollowedDTO userFollowedDTO = userService.getUserFollowedById(mockedUser1.getId(), order);

        // Assert
        assertNotNull(userFollowedDTO);
        Assertions.assertFalse(userFollowedDTO.getFollowed().isEmpty());
    }

    @Test
    @DisplayName("Should return a valid UserFollowedDTO as 'name_desc' is a valid order")
    void getUserFollowedById_validOrderRequestParam_nameDescHappyPath() {
        // Arrange
        String order = "name_desc";

        // Act
        when(userRepository.findUserById(anyInt())).thenReturn(mockedUser1);
        when(userRepository.getFollowRelationsByFollowerId(anyInt()))
                .thenReturn(mockedFollowedRelations);
        when(userRepository.getUserById(mockedUser2.getId())).thenReturn(mockedUser2);
        when(userRepository.getUserById(mockedUser3.getId())).thenReturn(mockedUser3);

        UserFollowedDTO userFollowedDTO = userService.getUserFollowedById(mockedUser1.getId(), order);

        // Assert
        assertNotNull(userFollowedDTO);
        Assertions.assertFalse(userFollowedDTO.getFollowed().isEmpty());
    }

    @Test
    @DisplayName("Should return a valid UserFollowedDTO as '' is a valid order")
    void getUserFollowedById_validOrderRequestParam_emptyHappyPath() {
        // Arrange
        String order = "";

        // Act
        when(userRepository.findUserById(anyInt())).thenReturn(mockedUser1);
        when(userRepository.getFollowRelationsByFollowerId(anyInt()))
                .thenReturn(mockedFollowedRelations);
        when(userRepository.getUserById(mockedUser2.getId())).thenReturn(mockedUser2);
        when(userRepository.getUserById(mockedUser3.getId())).thenReturn(mockedUser3);

        UserFollowedDTO userFollowedDTO = userService.getUserFollowedById(mockedUser1.getId(), order);

        // Assert
        assertNotNull(userFollowedDTO);
        Assertions.assertFalse(userFollowedDTO.getFollowed().isEmpty());
    }

    @Test
    @DisplayName("Should throw an exception as 'unexistingOrder' is not a valid order")
    void getUserFollowedById_validOrderRequestParam_sadPath() {
        // Arrange
        String order = "unexistingOrder";

        // Act & Assert
        when(userRepository.findUserById(anyInt())).thenReturn(mockedUser1);
        when(userRepository.getFollowRelationsByFollowerId(anyInt()))
                .thenReturn(mockedFollowedRelations);
        when(userRepository.getUserById(mockedUser2.getId())).thenReturn(mockedUser2);
        when(userRepository.getUserById(mockedUser3.getId())).thenReturn(mockedUser3);

        Assertions.assertThrows(BadRequestException.class, () -> userService.getUserFollowedById(mockedUser1.getId(), order));
    }

    @Test
    void getUserFollowedById() {
    }
}