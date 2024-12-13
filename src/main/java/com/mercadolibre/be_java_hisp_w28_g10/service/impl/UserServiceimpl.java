package com.mercadolibre.be_java_hisp_w28_g10.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowRelationDto;
import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowerDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.UserDto;
import com.mercadolibre.be_java_hisp_w28_g10.dto.UserFollowersDTO;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.IUserService;
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
