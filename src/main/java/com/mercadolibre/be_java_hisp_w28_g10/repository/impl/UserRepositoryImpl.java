package com.mercadolibre.be_java_hisp_w28_g10.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.ArrayList;
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
            throw new LoadJSONDataException("It wasn't possible to load JSON data for Users.");
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
    public FollowRelation saveFollow(int followerId, int followedId) {
        FollowRelation newFollow = new FollowRelation(followerId, followedId);
        followRelations.add(newFollow);
        return newFollow;
    }

    @Override
    public boolean existsUser(int userId) {
        return userList.stream().anyMatch(u -> u.getId() == userId );
    }

    @Override
    public boolean existsFollow(int followerId, int followedId) {
        return followRelations.stream().anyMatch(f -> f.getIdFollower() == followerId && f.getIdFollowed() == followedId);
    }

    @Override
    public User findUserById(int id) {
        return this.userList.stream().filter(u-> u.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void deleteFollowRelation(FollowRelation followRelation) {
        followRelations.remove(followRelation);
    }
}
