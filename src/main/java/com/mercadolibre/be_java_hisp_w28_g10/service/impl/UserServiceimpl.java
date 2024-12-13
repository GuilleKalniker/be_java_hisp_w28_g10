package com.mercadolibre.be_java_hisp_w28_g10.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowRelationDto;
import com.mercadolibre.be_java_hisp_w28_g10.dto.UserDto;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceimpl implements IUserService {
    @Autowired
    IUserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        ObjectMapper mapper = new ObjectMapper();
        return userRepository.findAllUsers().stream().map(u -> mapper.convertValue(u, UserDto.class)).toList();
    }

    @Override
    public List<UserDto> getAllUsersById(int userId) {
        ObjectMapper mapper = new ObjectMapper();
        return userRepository.findAllUsers().stream().map(u -> mapper.convertValue(u, UserDto.class)).toList();
    }

    @Override
    public List<FollowRelationDto> getAllFollowRelation() {
        ObjectMapper mapper = new ObjectMapper();
        return userRepository.findAllFollowRelation().stream().map(fr -> mapper.convertValue(fr, FollowRelationDto.class)).toList();
    }

    @Override
    public List<UserDto> getAllFollowedById(Integer userId) {
        ObjectMapper mapper = new ObjectMapper();
        List<FollowRelation> relations = userRepository.findAllFollowersRelationById(userId);
        List<Integer> ids = relations.stream()
                .map(FollowRelation::getIdFollowed)
                .toList();
        List<UserDto> users = userRepository.findAllUsers()
                .stream()
                .filter(user -> ids.contains(user.getId()))
                .map(user -> mapper.convertValue(user, UserDto.class)).toList();
        return List.of();
    }
}
