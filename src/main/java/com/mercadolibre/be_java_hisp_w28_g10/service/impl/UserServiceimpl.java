package com.mercadolibre.be_java_hisp_w28_g10.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.dto.UserDto;
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
}
