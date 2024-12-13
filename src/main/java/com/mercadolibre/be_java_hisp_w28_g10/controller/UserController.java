package com.mercadolibre.be_java_hisp_w28_g10.controller;

import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowRelationDto;
import com.mercadolibre.be_java_hisp_w28_g10.dto.UserDto;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> unfollowUserById(@PathVariable int userId, @PathVariable int userIdToUnfollow) {
        return new ResponseEntity<>(userService.unfollowUserById(userId, userIdToUnfollow), HttpStatus.OK);
    }




}
