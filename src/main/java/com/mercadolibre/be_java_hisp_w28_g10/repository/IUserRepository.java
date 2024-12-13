package com.mercadolibre.be_java_hisp_w28_g10.repository;

import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;

import java.util.List;

public interface IUserRepository {
    public List<User> findAllUsers();

    public List<FollowRelation> findAllFollowRelation();
    public User getUserById(int id);
    public List<FollowRelation> getFollowRelationsByFollowedId(int id);
}
