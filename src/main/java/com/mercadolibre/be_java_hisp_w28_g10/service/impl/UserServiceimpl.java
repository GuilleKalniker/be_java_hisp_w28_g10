package com.mercadolibre.be_java_hisp_w28_g10.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowRelationDto;
import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowersDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.UserDto;
import com.mercadolibre.be_java_hisp_w28_g10.exception.ConflictException;
import com.mercadolibre.be_java_hisp_w28_g10.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.IUserService;
import com.mercadolibre.be_java_hisp_w28_g10.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceimpl implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private Utilities utilities;


    @Override
    public List<UserDto> getAllUsers() {
        ObjectMapper mapper = new ObjectMapper();
        return userRepository.findAllUsers().stream().map(u -> mapper.convertValue(u, UserDto.class)).toList();
    }

    @Override
    public List<FollowRelationDto> getAllFollowRelation() {
        ObjectMapper mapper = new ObjectMapper();
        return userRepository.findAllFollowRelation().stream().map(fr -> mapper.convertValue(fr, FollowRelationDto.class)).toList();
    }

    @Override
    public FollowRelationDto follow(int followerId, int followedId) {
        if(!userRepository.existsUser(followerId)){
            throw new NotFoundException("Invalid UserId ");
        }
        if(!userRepository.existsUser(followedId)){
            throw new NotFoundException("Invalid userIdToFollow");
        }
        if(userRepository.existsFollow(followerId, followedId)){
            throw new ConflictException("The follow already exists");
        }
        FollowRelation newFollow = userRepository.saveFollow(followerId, followedId);
        return utilities.convertValue(newFollow, FollowRelationDto.class);
    }

    @Override
    public FollowersDTO getFollowersById(int id) {
        User user = userRepository.findUserById(id);
        if (user == null){
            throw new NotFoundException("User not found");
        }
        FollowersDTO followersDTO = new FollowersDTO(user.getId(), user.getName(), getAllFollowRelation().size());
        return followersDTO;
    }
}
