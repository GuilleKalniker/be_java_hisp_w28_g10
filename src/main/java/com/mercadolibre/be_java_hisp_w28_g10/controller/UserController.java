package com.mercadolibre.be_java_hisp_w28_g10.controller;

import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowRelationDto;
import com.mercadolibre.be_java_hisp_w28_g10.dto.UserDto;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/")
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping("getAll")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("followRelation/getAll")
    public ResponseEntity<List<FollowRelationDto>> getAllFollowRelations() {
        return new ResponseEntity<>(userService.getAllFollowRelation(), HttpStatus.OK);
    }

    @GetMapping("users/{userId}/followed/list")
    public ResponseEntity<List<UserDto>> getAllFollowedById(@PathVariable Integer userId) {
        return new ResponseEntity<>(userService.getAllFollowedById(userId), HttpStatus.OK);
    }

}
