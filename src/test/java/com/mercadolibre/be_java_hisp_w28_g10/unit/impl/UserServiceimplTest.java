package com.mercadolibre.be_java_hisp_w28_g10.unit.impl;

import com.mercadolibre.be_java_hisp_w28_g10.dto.follow.UserFollowersDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponseUserDTO;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.model.Product;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IProductRepository;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.impl.ProductServiceImpl;
import com.mercadolibre.be_java_hisp_w28_g10.service.impl.UserServiceimpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceimplTest {
    @Mock
    private IProductRepository productRepository;

    @Mock
    private IUserRepository userRepository;

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
    void follow() {
    }

    @Test
    void getFollowersAmountById() {
    }

    @Test
    void getUserFollowersById_validOrderRequestParam_nameAscHappyPath() {
        // Arrange
        int mockedUser1Id = 1;
        String mockedUser1Name = "Lorena";
        User mockedUser1 = new User(mockedUser1Id, mockedUser1Name);
        int mockedUser2Id = 2;
        String mockedUser2Name = "Pepe";
        User mockedUser2 = new User(mockedUser2Id, mockedUser2Name);
        int mockedUser3Id = 3;
        String mockedUser3Name = "Juan";
        User mockedUser3 = new User(mockedUser3Id, mockedUser3Name);

        String order = "name_asc";

        List<FollowRelation> mockedFollowRelationsByFollowedId = List.of(
                new FollowRelation(2,1),
                new FollowRelation(3,1)
        );

        // Act
        Mockito.when(userRepository.getUserById(mockedUser1Id)).thenReturn(mockedUser1);
        Mockito.when(userRepository.getFollowRelationsByFollowedId(mockedUser1Id))
                .thenReturn(mockedFollowRelationsByFollowedId);
        Mockito.when(userRepository.getUserById(mockedUser2Id)).thenReturn(mockedUser2);
        Mockito.when(userRepository.getUserById(mockedUser3Id)).thenReturn(mockedUser3);

        UserFollowersDTO userFollowersDTO = userService.getUserFollowersById(mockedUser1Id, order);

        // Assert
        Assertions.assertNotNull(userFollowersDTO);
        Assertions.assertFalse(userFollowersDTO.getFollowers().isEmpty());
    }

    @Test
    void getUserFollowersById_validOrderRequestParam_nameDescHappyPath() {

    }

    @Test
    void getUserFollowersById_validOrderRequestParam_emptyHappyPath() {
    }

    @Test
    void getUserFollowersById_validOrderRequestParam_sadPath() {
    }

    @Test
    void getUserFollowedById() {
    }
}