package com.mercadolibre.be_java_hisp_w28_g10.unit.impl;

import com.mercadolibre.be_java_hisp_w28_g10.DatosMock;
import com.mercadolibre.be_java_hisp_w28_g10.dto.follow.ResponseFollowedPostsDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponsePostNoPromoDTO;
import com.mercadolibre.be_java_hisp_w28_g10.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IProductRepository;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.IProductService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mercadolibre.be_java_hisp_w28_g10.service.impl.ProductServiceImpl;
import com.mercadolibre.be_java_hisp_w28_g10.util.Utilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private Utilities utilities;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllProducts() {
    }

    @Test
    void getAllPost() {
    }

    @Test
    void addPromoPost() {
    }

    @Test
    void addPost() {
    }

    @Test
    void productsWithPromoDTO() {
    }

    @Test
    void getLastFollowedPosts() {
    }

    @Test
    void getLastFollowedPosts_ascendingOrderArgumentIsValid_happyPathOrderExists() {
        //ARRANGE
        Integer userId = 2;

        when(productRepository.findAllPost()).thenReturn(DatosMock.POST_LIST);
        when(userRepository.getFollowRelationsByFollowerId(2)).thenReturn(DatosMock.FOLLOW_RELATIONS_2);
        when(userRepository.findUserById(2)).thenReturn(DatosMock.USER_2);
        String order = "date_asc";

        //ACT
        ResponseFollowedPostsDTO result = productService.getLastFollowedPosts(userId, order);

        //ASSERT
        assertNotNull(result, "Result should not be null");
    }

    @Test
    void getLastFollowedPosts_ascendingOrderArgumentIsValid_happyPathAscendingOrderIsValid() {
        //ARRANGE
        Integer userId = 2;

        when(productRepository.findAllPost()).thenReturn(DatosMock.POST_LIST);
        when(userRepository.getFollowRelationsByFollowerId(2)).thenReturn(DatosMock.FOLLOW_RELATIONS_2);
        when(userRepository.findUserById(2)).thenReturn(DatosMock.USER_2);
        String order = "date_asc";

        //ACT
        ResponseFollowedPostsDTO result = productService.getLastFollowedPosts(userId, order);

        //ASSERT
        assertNotNull(result, "Result should not be null");
        assertTrue(IntStream.range(1, result.getPostDTOList().size())
                .allMatch(i -> {
                    List<ResponsePostNoPromoDTO> list = result.getPostDTOList();
                    String fechaPostString = list.get(i).getDate();
                    LocalDate fechaPost = LocalDate.parse(fechaPostString);
                    return !fechaPost.isBefore(LocalDate.parse(list.get(i - 1).getDate()));
                })
        );
    }

    @Test
    void getLastFollowedPosts_descendingOrderArgumentIsValid_happyPathDescendingOrderIsValid() {
        //ARRANGE
        Integer userId = 2;

        when(productRepository.findAllPost()).thenReturn(DatosMock.POST_LIST);
        when(userRepository.getFollowRelationsByFollowerId(2)).thenReturn(DatosMock.FOLLOW_RELATIONS_2);
        when(userRepository.findUserById(2)).thenReturn(DatosMock.USER_2);
        String order = "date_desc";

        //ACT
        ResponseFollowedPostsDTO result = productService.getLastFollowedPosts(userId, order);

        //ASSERT
        assertNotNull(result, "Result should not be null");
        assertTrue(IntStream.range(1, result.getPostDTOList().size())
                .allMatch(i -> {
                    List<ResponsePostNoPromoDTO> list = result.getPostDTOList();
                    String fechaPostString = list.get(i).getDate();
                    LocalDate fechaPost = LocalDate.parse(fechaPostString);
                    return fechaPost.isBefore(LocalDate.parse(list.get(i - 1).getDate()));
                })

        );
    }

    @Test
    void getLastFollowedPosts_ordenationOrderArgumentIsNotValid_throwsException() {
        //ARRANGE
        Integer userId = 2;

        when(productRepository.findAllPost()).thenReturn(DatosMock.POST_LIST);
        when(userRepository.getFollowRelationsByFollowerId(2)).thenReturn(DatosMock.FOLLOW_RELATIONS_2);
        when(userRepository.findUserById(2)).thenReturn(DatosMock.USER_2);
        String order = "invalid_criteria";

        //ACT & ASSERT
        assertThrows(BadRequestException.class, () -> productService.getLastFollowedPosts(userId, order));

    }
}