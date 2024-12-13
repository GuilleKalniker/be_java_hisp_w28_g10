package com.mercadolibre.be_java_hisp_w28_g10.service;

import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowRelationDto;
import com.mercadolibre.be_java_hisp_w28_g10.dto.UserDto;
import com.mercadolibre.be_java_hisp_w28_g10.dto.UserFollowersDTO;

import java.util.List;

public interface IUserService {
    public List<UserDto> getAllUsers();
    public List<FollowRelationDto> getAllFollowRelation();
    public UserFollowersDTO getUserFollowers(int userId);
}
