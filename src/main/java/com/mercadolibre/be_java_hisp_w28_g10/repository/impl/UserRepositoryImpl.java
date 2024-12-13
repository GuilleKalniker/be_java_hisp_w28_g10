package com.mercadolibre.be_java_hisp_w28_g10.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    private List<User> userList = List.of();

    @PostConstruct
    public void init() {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getResourceAsStream("/users.json")) {
            userList = objectMapper.readValue(inputStream, new TypeReference<List<User>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            userList = List.of();
        }
    }

    @Override
    public List<User> findAllUsers() {
        return userList;
    }
}
