package com.mercadolibre.be_java_hisp_w28_g10.service;

import com.mercadolibre.be_java_hisp_w28_g10.dto.UserDto;

import java.util.List;

public interface IUserService {
    public List<UserDto> getAllUsers();
}
