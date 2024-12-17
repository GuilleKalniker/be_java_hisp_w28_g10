package com.mercadolibre.be_java_hisp_w28_g10.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mercadolibre.be_java_hisp_w28_g10.exception.LoadJSONDataException;
import com.mercadolibre.be_java_hisp_w28_g10.model.Post;
import com.mercadolibre.be_java_hisp_w28_g10.model.Product;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IProductRepository;
import com.mercadolibre.be_java_hisp_w28_g10.util.Utilities;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements IProductRepository {
    @Autowired
    private Utilities utilities;
    private List<Product> productList = new ArrayList<>();
    private List<Post> postList = new ArrayList<>();


    @PostConstruct
    public void init() {
        try (
                InputStream inputStream = getClass().getResourceAsStream("/products.json");
                InputStream inputStreamPost = getClass().getResourceAsStream("/post.json");) {
            productList = utilities.readValue(inputStream, new TypeReference<>() {
            });
            postList = utilities.readValue(inputStreamPost, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new LoadJSONDataException("It wasn't possible to load JSON data for Products or Post.");
        }
    }

    @Override
    public List<Product> findAll() {
        return productList;
    }

    @Override
    public List<Post> findAllPost() {
        return postList;
    }

    @Override
    public boolean existsProduct(int productId) {
        return productList.stream().anyMatch(product -> product.getId() == productId);
    }

    @Override
    public boolean addProduct(Product product) {
        return productList.add(product);
    }

    @Override
    public boolean addPost(Post post) {
        return postList.add(post);
    }
}
