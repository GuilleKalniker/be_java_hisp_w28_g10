package com.mercadolibre.be_java_hisp_w28_g10.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowRelationDto;
import com.mercadolibre.be_java_hisp_w28_g10.dto.UserDto;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.UnfollowUserByIdDTO;
import com.mercadolibre.be_java_hisp_w28_g10.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<FollowRelationDto> getAllFollowRelation() {
        ObjectMapper mapper = new ObjectMapper();
        return userRepository.findAllFollowRelation().stream().map(fr -> mapper.convertValue(fr, FollowRelationDto.class)).toList();
    }

    @Override
    public UnfollowUserByIdDTO unfollowUserById(int userId, int userIdToUnfollow) {
        List<FollowRelation> followRelations = userRepository.findAllFollowRelation();

        FollowRelation followRelation = followRelations.stream()
                .filter(fr -> fr.getIdFollower() == userId && fr.getIdFollowed() == userIdToUnfollow)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No follower relationship found for the given ids"));

        userRepository.deleteFollowRelation(followRelation);
        return new UnfollowUserByIdDTO("The user with id:" + userIdToUnfollow + " was unfollowed successfully.");

    }
}
