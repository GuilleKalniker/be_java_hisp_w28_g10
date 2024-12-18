package com.mercadolibre.be_java_hisp_w28_g10.service.impl;

import com.mercadolibre.be_java_hisp_w28_g10.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponseMessageDTO;
import com.mercadolibre.be_java_hisp_w28_g10.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w28_g10.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.exception.ConflictException;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.IUserService;
import com.mercadolibre.be_java_hisp_w28_g10.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.*;

@Service
public class UserServiceimpl implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private Utilities utilities;


    @Override
    public List<UserDTO> getAllUsers() {
        ObjectMapper mapper = new ObjectMapper();
        return userRepository.findAllUsers().stream().map(u -> mapper.convertValue(u, UserDTO.class)).toList();
    }

    @Override
    public List<FollowRelationDTO> getAllFollowRelation() {
        ObjectMapper mapper = new ObjectMapper();
        return userRepository.findAllFollowRelation().stream().map(fr -> mapper.convertValue(fr, FollowRelationDTO.class)).toList();
    }

    @Override
    public ResponseMessageDTO unfollowUserById(int userId, int userIdToUnfollow) {
        List<FollowRelation> followRelations = userRepository.findAllFollowRelation();

        FollowRelation followRelation = followRelations.stream()
                .filter(fr -> fr.getIdFollower() == userId && fr.getIdFollowed() == userIdToUnfollow)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No follower relationship found for the given ids"));

        if (!userRepository.deleteFollowRelation(followRelation)) {
            throw new BadRequestException("Couldn´t delete the follow relation");
        }

        return new ResponseMessageDTO("The user with id:" + userIdToUnfollow + " was unfollowed successfully.");
    }

    @Override
    public FollowRelationDTO follow(int followerId, int followedId) {
        if (!userRepository.existsUser(followerId)) {
            throw new NotFoundException("Invalid UserId ");
        }
        if (!userRepository.existsUser(followedId)) {
            throw new NotFoundException("Invalid userIdToFollow");
        }
        if (userRepository.existsFollow(followerId, followedId)) {
            throw new ConflictException("The follow already exists");
        }
        if (followedId == followerId) {
            throw new ConflictException("The user cannot follow itself");
        }
        FollowRelation newFollow = userRepository.saveFollow(followerId, followedId);
        return utilities.convertValue(newFollow, FollowRelationDTO.class);
    }

    @Override
    public FollowersDTO getFollowersAmountById(int id) {
        //I get a User for user id
        User user = userRepository.findUserById(id);
        //I get a List of followRelation of followers and followed
        List<FollowRelation> followRelation = userRepository.findAllFollowRelation();
        //Validate if the user is null
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        //Filter of FollowRelation by user id
        List<FollowRelation> followedFilter = followRelation.stream()
                .filter(f -> f.getIdFollowed() == user.getId())
                .toList();
        //return a DTO
        return new FollowersDTO(user.getId(), user.getName(), followedFilter.size());
    }

    @Override
    public UserFollowersDTO getUserFollowersById(int userId, String order) {

        // Valido si existe user con ese userId;
        User user = userRepository.getUserById(userId);
        List<FollowRelation> followRelationsByFollowedId = userRepository.getFollowRelationsByFollowedId(userId);

        List<ResponseUserDTO> followers = getRelatedUsersById(followRelationsByFollowedId, false, order);
        return new UserFollowersDTO(user.getId(), user.getName(), followers);
    }

    @Override
    public UserFollowersDTO getUserFollowedById(Integer userId, String order) {

        // Valido si existe user con ese userId;
        User user = userRepository.getUserById(userId);
        List<FollowRelation> followRelationsByFollowerId = userRepository.getFollowRelationsByFollowerId(userId);

        List<ResponseUserDTO> followers = getRelatedUsersById(followRelationsByFollowerId, true, order);
        return new UserFollowersDTO(user.getId(), user.getName(), followers);
    }

    private List<ResponseUserDTO> getRelatedUsersById(List<FollowRelation> followRelations, boolean isFollower, String order) {

        List<ResponseUserDTO> responseUserDtos = followRelations.stream()
                    .map(followRelation -> {
                        User user;
                        if(isFollower) {
                            user = userRepository.getUserById(followRelation.getIdFollowed());
                        } else {
                            user = userRepository.getUserById(followRelation.getIdFollower());
                        }
                        return new ResponseUserDTO(user.getId(), user.getName());
                    })
                    .toList();

        if(!order.equalsIgnoreCase("name_asc") && !order.equalsIgnoreCase("name_desc") && !order.isEmpty()) {
            throw new BadRequestException("Invalid order, please set a valid order param");
        }

        return orderFollowersByName(responseUserDtos, order);
    }

    private List<ResponseUserDTO> orderFollowersByName(List<ResponseUserDTO> responseUsers,String order) {
        if(order.isEmpty()) {
            return responseUsers;
        } else if(order.equalsIgnoreCase("name_asc")) {
            return responseUsers.stream()
                    .sorted(Comparator.comparing(ResponseUserDTO::getName))
                    .toList();
        }
        return responseUsers.stream()
                    .sorted(Comparator.comparing(ResponseUserDTO::getName).reversed()).toList();
    }
}
