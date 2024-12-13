package com.mercadolibre.be_java_hisp_w28_g10.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowRelationDto;
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
import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    @Autowired
    private Utilities utilities;
    private List<User> userList = List.of();
    private List<FollowRelation> followRelations = List.of(
//            new FollowRelation(1, 2, LocalDate.of(2024, 10, 10)),
//            new FollowRelation(1, 3, LocalDate.of(2024, 10, 10)),
//            new FollowRelation(2, 1, LocalDate.of(2024, 10, 10)),
//            new FollowRelation(2, 1, LocalDate.of(2024, 10, 10))
            new FollowRelation(1, 2, "2024/10/12"),
            new FollowRelation(1, 3, "2024/10/12"),
            new FollowRelation(2, 1, "2024/10/12"),
            new FollowRelation(2, 1, "2024/10/12")
    );


    @PostConstruct
    public void init() {

        try (InputStream inputStreamUsers = getClass().getResourceAsStream("/users.json");
             //InputStream inputStreamFollowRelations = getClass().getResourceAsStream("/follow_relation.json");
        ) {
            userList = utilities.readValue(inputStreamUsers, new TypeReference<>() {
            });
//            followRelations = utilities.readValue(inputStreamFollowRelations, new TypeReference<>() {
//            });
        } catch (IOException e) {
            e.printStackTrace();
            userList = List.of();
            followRelations = List.of();
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

    @Override
    public List<FollowRelation> findAllFollowersRelationById(int id) {
        return followRelations.stream()
                .filter(followRelation -> followRelation.getIdFollowed() == id).toList();
    }
}
