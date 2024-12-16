package com.mercadolibre.be_java_hisp_w28_g10.service;

import com.mercadolibre.be_java_hisp_w28_g10.dto.*;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponseMessageDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponsePostNoPromoDTO;

import java.util.List;

public interface IUserService {
    public List<UserDTO> getAllUsers();
    public List<FollowRelationDTO> getAllFollowRelation();
    public ResponseMessageDTO unfollowUserById(int userId, int userIdToUnfollow);
    public FollowRelationDTO follow(int followerId, int followedId);
    public FollowersDTO getFollowersById(int id);
    public UserFollowersDTO getUserFollowers(int userId);
}
