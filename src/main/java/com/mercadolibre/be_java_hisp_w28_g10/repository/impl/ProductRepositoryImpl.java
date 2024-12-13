package com.mercadolibre.be_java_hisp_w28_g10.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.exception.LoadJSONDataException;
import com.mercadolibre.be_java_hisp_w28_g10.model.Product;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IProductRepository;
import com.mercadolibre.be_java_hisp_w28_g10.util.Utilities;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements IProductRepository {
    @Autowired
    private Utilities utilities;
    private List<Product> productList = new ArrayList<>();

    @PostConstruct
    public void init() {
        try (InputStream inputStream = getClass().getResourceAsStream("/products.json")) {
            productList = utilities.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new LoadJSONDataException("It wasn't possible to load JSON data for Products.");
        }
    }

    @Override
    public List<Product> findAll() {
        return productList;
    }
}
