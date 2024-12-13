package com.mercadolibre.be_java_hisp_w28_g10.service;

import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowRelationDto;
import com.mercadolibre.be_java_hisp_w28_g10.dto.UserDto;

import java.util.List;

public interface IUserService {
    public List<UserDto> getAllUsers();
    public List<FollowRelationDto> getAllFollowRelation();
    List<UserDto> getAllFollowedById(Integer userId);
    public List<UserDto> getAllUsersById(int userId);
}
