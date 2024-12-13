package com.mercadolibre.be_java_hisp_w28_g10.service;

import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowRelationDto;
import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowersDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.UserDto;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.UnfollowUserByIdDTO;

import java.util.List;

public interface IUserService {
    public List<UserDto> getAllUsers();
    public List<FollowRelationDto> getAllFollowRelation();
    public UnfollowUserByIdDTO unfollowUserById(int userId, int userIdToUnfollow);
    public FollowRelationDto follow(int followerId, int followedId);
    public FollowersDTO getFollowersById(int id);
}
