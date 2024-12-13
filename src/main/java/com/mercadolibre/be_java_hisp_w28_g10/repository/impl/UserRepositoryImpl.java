package com.mercadolibre.be_java_hisp_w28_g10.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadolibre.be_java_hisp_w28_g10.exception.LoadJSONDataException;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.util.Utilities;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    @Autowired
    private Utilities utilities;
    private List<User> userList = new ArrayList<>();
    private List<FollowRelation> followRelations = new ArrayList<>();


    @PostConstruct
    public void init() {
        try (InputStream inputStreamUsers = getClass().getResourceAsStream("/users.json");
             InputStream inputStreamFollowRelations = getClass().getResourceAsStream("/follow_relation.json");
        ) {
            userList = utilities.readValue(inputStreamUsers, new TypeReference<>() {
            });
            followRelations = utilities.readValue(inputStreamFollowRelations, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new LoadJSONDataException("No fue posible cargar los datos del JSON de usuarios.");
        }
    }

    @Override
    public List<User> findAllUsers() {
        return userList;
    }

    @Override
    public List<FollowRelation> findAllFollowRelation() {
        return followRelations;
    }
}
