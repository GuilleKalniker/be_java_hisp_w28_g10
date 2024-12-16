package com.mercadolibre.be_java_hisp_w28_g10.service.impl;

import com.mercadolibre.be_java_hisp_w28_g10.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponseMessageDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponsePostNoPromoDTO;
import com.mercadolibre.be_java_hisp_w28_g10.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w28_g10.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.exception.ConflictException;
import com.mercadolibre.be_java_hisp_w28_g10.model.Post;
import com.mercadolibre.be_java_hisp_w28_g10.model.Product;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.IUserService;
import com.mercadolibre.be_java_hisp_w28_g10.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        List<FollowRelation> followRelation = userRepository.findAllFollowRelation();
        if (user == null){
            throw new NotFoundException("User not found");
        }
        List<FollowRelation> followedFilter = followRelation.stream()
                .filter(f-> f.getIdFollowed() == user.getId())
                .toList();
        return new FollowersDTO(user.getId(), user.getName(), followedFilter.size());
    }

    @Override
    public UserFollowersDTO getUserFollowers(int userId) {

        // Valido si existe user con ese userId;
        User user = userRepository.getUserById(userId);

        // TODO: Refactorizar este metodo para solo traer los id de los followers
        List<FollowRelation> followRelations = userRepository.getFollowRelationsByFollowedId(userId);

        List<ResponseUserDTO> followersDto = followRelations.stream()
                .map(followRelation -> {
                    User follower = userRepository.getUserById(followRelation.getIdFollower());
                    return new ResponseUserDTO(follower.getId(), follower.getName());
                })
                .collect(Collectors.toList());

        // Crear y retornar el DTO de usuario con la lista de seguidores
        return new UserFollowersDTO(user.getId(), user.getName(), followersDto);
    }

    @Override
    public ResponsePostNoPromoDTO addPost(PostDTO newPost) {

        if(userRepository.existsPost(newPost.getId(), newPost.getProductDto().getId())) {
            throw new ConflictException("The post already exists");
        }

        // Valido que los atributos del postDTO y ProductDTO sean validos.
        validatePostDto(newPost);
        validateProductDto(newPost.getProductDto());


        // No va a ser necesario cuando haga el pr con Will
        LocalDate postDate = PostDTO.parseStringToLocalDate(newPost.getDate());

        Product product = utilities.convertValue(newPost.getProductDto(), Product.class);
        Post post = new Post(newPost.getId(),
                            postDate,
                            newPost.getCategory(),
                            newPost.getPrice(),
                            product,
                   false,
                    1); // o nulo

        userRepository.addPost(post);

        ProductDTO productDto = utilities.convertValue(post.getProduct(), ProductDTO.class);

        return new ResponsePostNoPromoDTO(post.getId(), post.getDate().toString(), post.getCategory(), post.getPrice(),
                productDto);
    }

    private void validatePostDto(PostDTO post) {
        if (post.getId() <= 0) {
            throw new IllegalArgumentException("The ID must be a positive number.");
        }
        if (post.getDate() == null || post.getDate().isEmpty()) {
            throw new IllegalArgumentException("Date is required.");
        }
        if (post.getCategory() <= 0) {
            throw new IllegalArgumentException("Category must be a positive value.");
        }
        if (post.getPrice() <= 0) {
            throw new IllegalArgumentException("The price must be a positive number.");
        }
    }

    private void validateProductDto(ProductDTO productDto) {
        if (productDto.getId() <= 0) {
            throw new IllegalArgumentException("The product ID must be a positive number.");
        }
        if (productDto.getName() == null || productDto.getName().isEmpty()) {
            throw new IllegalArgumentException("The product name is required.");
        }
        if (productDto.getType() == null || productDto.getType().isEmpty()) {
            throw new IllegalArgumentException("The product type is required.");
        }
        if (productDto.getBrand() == null || productDto.getBrand().isEmpty()) {
            throw new IllegalArgumentException("The product brand is required.");
        }
        if (productDto.getColor() == null || productDto.getColor().isEmpty()) {
            throw new IllegalArgumentException("The product color is required.");
        }
    }
}
