package com.mercadolibre.be_java_hisp_w28_g10.unit.impl;

import com.mercadolibre.be_java_hisp_w28_g10.DatosMock;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponseMessageDTO;
import com.mercadolibre.be_java_hisp_w28_g10.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w28_g10.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IProductRepository;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.impl.UserServiceimpl;
import com.mercadolibre.be_java_hisp_w28_g10.util.Utilities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
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

        when(userRepository.findAllFollowRelation()).thenReturn(List.of());

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
    void follow() {
    }

    @Test
    void getFollowersAmountById() {
    }

    @Test
    void getUserFollowersById() {
    }

    @Test
    void getUserFollowedById() {
    }
}