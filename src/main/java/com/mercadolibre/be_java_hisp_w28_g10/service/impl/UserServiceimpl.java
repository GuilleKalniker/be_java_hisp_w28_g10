package com.mercadolibre.be_java_hisp_w28_g10.service.impl;

import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowerDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.UserFollowersDTO;
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
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        if (user == null){
            throw new NotFoundException("User not found");
        }
        FollowersDTO followersDTO = new FollowersDTO(user.getId(), user.getName(), getAllFollowRelation().size());
        return followersDTO;
    }

    @Override
    public UserFollowersDTO getUserFollowers(int userId) {

        // Valido si existe user con ese userId;
        User user = userRepository.getUserById(userId);

        // TODO: Refactorizar este metodo para solo traer los id de los followers
        List<FollowRelation> followRelations = userRepository.getFollowRelationsByFollowedId(userId);

        List<FollowerDTO> followersDto = followRelations.stream()
                .map(followRelation -> {
                    User follower = userRepository.getUserById(followRelation.getIdFollower());
                    return new FollowerDTO(follower.getId(), follower.getName());
                })
                .collect(Collectors.toList());

        // Crear y retornar el DTO de usuario con la lista de seguidores
        return new UserFollowersDTO(user.getId(), user.getName(), followersDto);
    }
}
