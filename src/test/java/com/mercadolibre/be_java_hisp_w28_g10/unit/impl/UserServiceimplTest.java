package com.mercadolibre.be_java_hisp_w28_g10.unit.impl;

import com.mercadolibre.be_java_hisp_w28_g10.dto.follow.FollowersDTO;
import com.mercadolibre.be_java_hisp_w28_g10.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IProductRepository;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.impl.UserServiceimpl;
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
    void follow() {
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
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.getFollowersAmountById(1));
        //ASSERT
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void getUserFollowedById() {
    }
}