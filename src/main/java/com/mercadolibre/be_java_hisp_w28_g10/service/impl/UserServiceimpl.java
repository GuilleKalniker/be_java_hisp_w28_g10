package com.mercadolibre.be_java_hisp_w28_g10.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowRelationDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowersDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.UserDTO;
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

import java.util.List;

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

        if(!userRepository.deleteFollowRelation(followRelation)) {
            throw new BadRequestException("Couldn´t delete the follow relation");
        }

        return new ResponseMessageDTO("The user with id:" + userIdToUnfollow + " was unfollowed successfully.");
    }

    @Override
    public FollowRelationDTO follow(int followerId, int followedId) {
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
        return utilities.convertValue(newFollow, FollowRelationDTO.class);
    }

    @Override
    public FollowersDTO getFollowersById(int id) {
        User user = userRepository.findUserById(id);
        List<FollowRelation> followRelation = userRepository.findAllFollowRelation();
        if (user == null){
            throw new NotFoundException("User not found");
        }
        List<FollowRelation> followedFilter = followRelation.stream()
                .filter(f-> f.getIdFollowed() == user.getId())
                .toList();
        return new FollowersDTO(user.getId(), user.getName(), followedFilter.size());
    }
}
