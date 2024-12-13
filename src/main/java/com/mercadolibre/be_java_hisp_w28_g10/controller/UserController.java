package com.mercadolibre.be_java_hisp_w28_g10.controller;

import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowRelationDto;
import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowersDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.UserDto;
import com.mercadolibre.be_java_hisp_w28_g10.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("{userId}/follow/{userIdToFollow}")
    public ResponseEntity<FollowRelationDto> addNewFollow(@PathVariable int userId, @PathVariable int userIdToFollow) {
        return new ResponseEntity<>(userService.follow(userId, userIdToFollow), HttpStatus.OK);
    }
    @GetMapping("/users/{userId}/followers/count")
    public ResponseEntity<FollowersDTO> getFollowersById(@PathVariable int userId){
        return new ResponseEntity<>(userService.getFollowersById(userId), HttpStatus.OK);
    }

}
