package com.mercadolibre.be_java_hisp_w28_g10.unit.impl;

import com.mercadolibre.be_java_hisp_w28_g10.repository.IProductRepository;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.IProductService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mercadolibre.be_java_hisp_w28_g10.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private IUserRepository userRepository;

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
}