package com.mercadolibre.be_java_hisp_w28_g10.service.impl;

import com.mercadolibre.be_java_hisp_w28_g10.dto.PostDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.ProductDTO;
import com.mercadolibre.be_java_hisp_w28_g10.exception.SaveOperationException;
import com.mercadolibre.be_java_hisp_w28_g10.model.Post;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponsePostNoPromoDTO;
import com.mercadolibre.be_java_hisp_w28_g10.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.dto.*;
import com.mercadolibre.be_java_hisp_w28_g10.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IProductRepository;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.IProductService;
import com.mercadolibre.be_java_hisp_w28_g10.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private Utilities utilities;

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(p -> utilities.convertValue(p, ProductDTO.class)).toList();
    }

    @Override
    public List<PostDTO> getAllPost() {
        return productRepository.findAllPost().stream().map(p -> utilities.convertValue(p, PostDTO.class)).toList();
    }

    @Override
    public PostDTO addPromoPost(PostDTO post) {
        if (!savePostLogic(post)) throw new SaveOperationException("Couldn't make the save operation.");
        return post;
    }

    @Override
    public ResponsePostNoPromoDTO addPost(PostDTO newPost) {
        if (!savePostLogic(newPost)) throw new SaveOperationException("Couldn't make the save operation.");
        Post post = utilities.convertValue(newPost, Post.class);
        ProductDTO productDto = utilities.convertValue(post.getProduct(), ProductDTO.class);

        return new ResponsePostNoPromoDTO(post.getId(), post.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), post.getCategory(), post.getPrice(),
                productDto);
    }

    private boolean savePostLogic(PostDTO post) {
        validatePostDto(post);
        validateProductDto(post.getProduct());
        Product product = utilities.convertValue(post.getProduct(), Product.class);
        if (!productRepository.existsProduct(post.getProduct().getId())) {
            productRepository.addProduct(product);
        }
        return productRepository.addPost(utilities.convertValue(post, Post.class));
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

    @Override
    public ResponseFollowedPostsDTO getLastFollowedPosts(Integer userId) {

        List<PostDTO> postDTOList = getFollowedProductsByUserId(userId);

        //Preservar esta estructura para poder reimplementarla en un filtro mas adelante
        LocalDate twoWeeksAgo = LocalDate.now().minusWeeks(2);

        List<PostDTO> postDTOSfromTwoWeeksAgo = postDTOList.stream()
                .filter(postDTO -> postDTO
                        .getDate()
                        .isAfter(twoWeeksAgo))
                .sorted(Comparator.comparing(PostDTO::getDate).reversed())
                .toList();

        if (postDTOSfromTwoWeeksAgo.isEmpty()){throw new BadRequestException("There are no posts from two weeks ago");};

        return new ResponseFollowedPostsDTO(userId, postDTOSfromTwoWeeksAgo);
    }

    private List<PostDTO> getFollowedProductsByUserId(Integer userId) {
        //Obtengo la lista de ID de usuarios relacionados
        List<FollowRelation>followRelations = userRepository.getFollowRelationsByFollowerId(userId);
        if(followRelations.isEmpty()){throw new BadRequestException("This user is following no one");}

        //Obtengo en base a esos mismos ids, la lista de los user objects
        List<User> followers = getFolloweds(followRelations);

        //Obtengo una lista de elementos post en base a esa misma lista de usuarios
        return getPostsByUserList(followers);
    }

    private List<User> getFolloweds(List<FollowRelation> followRelations){
        return followRelations.stream()
                .map(followRelation -> {
                    return userRepository.getUserById(followRelation.getIdFollowed());
                })
                .toList();
    }

    private List<PostDTO> getPostsByUserList(List<User> userList){
        List<PostDTO> postDTOList = userList.stream()
                .flatMap(follower -> follower.getPostList().stream())
                .map(post -> utilities.convertValue(post, PostDTO.class))
                .toList();

        if (postDTOList.isEmpty()){throw new BadRequestException("There are no products posted by your followed sellers");}
        return postDTOList;
    }
}
