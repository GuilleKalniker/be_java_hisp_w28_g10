package com.mercadolibre.be_java_hisp_w28_g10.controller;

import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowRelationDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.FollowersDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.UserDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponseMessageDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.UserFollowersDTO;
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
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
    @GetMapping("followRelation/getAll")
    public ResponseEntity<List<FollowRelationDTO>> getAllFollowRelations() {
        return new ResponseEntity<>(userService.getAllFollowRelation(), HttpStatus.OK);
    }
    @PostMapping("{userId}/follow/{userIdToFollow}")
    public ResponseEntity<FollowRelationDTO> addNewFollow(@PathVariable int userId, @PathVariable int userIdToFollow) {
        return new ResponseEntity<>(userService.follow(userId, userIdToFollow), HttpStatus.OK);
    }
    @GetMapping("users/{userId}/followers/count")
    public ResponseEntity<FollowersDTO> getFollowersById(@PathVariable int userId){
        return new ResponseEntity<>(userService.getFollowersById(userId), HttpStatus.OK);
    }
    @PostMapping("{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<ResponseMessageDTO> unfollowUserById(@PathVariable int userId, @PathVariable int userIdToUnfollow) {
        return new ResponseEntity<>(userService.unfollowUserById(userId, userIdToUnfollow), HttpStatus.OK);
    }

    @GetMapping("{userId}/followers/list")
    public ResponseEntity<UserFollowersDTO> getUserFollowers(@PathVariable int userId) {
        return new ResponseEntity<>(userService.getUserFollowers(userId), HttpStatus.OK);
    }

    @GetMapping("users/{userId}/followed/list")
    public ResponseEntity<UserFollowersDTO> getUserFollowed(@PathVariable Integer userId) {
        return new ResponseEntity<>(userService.getUserFollowed(userId), HttpStatus.OK);
    }

}
