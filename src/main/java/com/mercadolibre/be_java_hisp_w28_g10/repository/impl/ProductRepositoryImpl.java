package com.mercadolibre.be_java_hisp_w28_g10.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.model.Product;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements IProductRepository {
    private List<Product> productList = List.of();

    @PostConstruct
    public void init() {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getResourceAsStream("/products.json")) {
            productList = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            productList = List.of();
        }
    }

    @Override
    public List<Product> findAll() {
        return productList;
    }
}
