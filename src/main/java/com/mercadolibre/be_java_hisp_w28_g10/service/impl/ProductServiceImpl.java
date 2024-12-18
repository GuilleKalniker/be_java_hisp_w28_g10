package com.mercadolibre.be_java_hisp_w28_g10.service.impl;

import com.mercadolibre.be_java_hisp_w28_g10.dto.PostDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.ProductDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ProductsWithPromoDTO;
import com.mercadolibre.be_java_hisp_w28_g10.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w28_g10.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w28_g10.model.Post;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponsePostNoPromoDTO;
import com.mercadolibre.be_java_hisp_w28_g10.model.Product;
import com.mercadolibre.be_java_hisp_w28_g10.dto.*;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
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
import java.util.Optional;

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
        savePostLogic(post);
        return post;
    }

    @Override
    public ResponsePostNoPromoDTO addPost(PostDTO newPost) {
        savePostLogic(newPost);
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
        try {
            LocalDate ld = utilities.convertValue(post.getDate(), LocalDate.class);
        } catch (Exception e) {
            throw new BadRequestException("The date field must be in the dd/MM/yyyy format.");
        }

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
    public ProductsWithPromoDTO productsWithPromoDTO(int id) {
        User user = userRepository.findUserById(id);
        List<Post> product = productRepository.findAllPost();
        if (user == null || product.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        List<Post> productFilter = product.stream()
                .filter(p -> p.getId() == user.getId()).filter(Post::isHasPromo)
                .toList();
        return new ProductsWithPromoDTO(user.getId(), user.getName(), productFilter.size());
    }

    @Override
    public ResponseFollowedPostsDTO getLastFollowedPosts(Integer userId, Optional<String> order) {

        //Time filter. Two Weeks
        LocalDate twoWeeksAgo = LocalDate.now().minusWeeks(2);

        //Get the ID list of related users
        List<Integer> followedIds = userRepository.getFollowRelationsByFollowerId(userId)
                .stream()
                .map(FollowRelation::getIdFollowed)
                .toList();

        //Exception. Following no one
        if(followedIds.isEmpty())throw new BadRequestException("You are following no one");

        //Get the list of every post
        List<Post> postList = productRepository.findAllPost();

        //Filter by gollowed users
        List<Post> postListByUserId = postList.stream().filter(post -> followedIds.contains(post.getId())).toList();

        //Filter to get the posts in the specified time
        List<Post> followedPostsfromTwoWeeksAgo = postListByUserId
                .stream()
                .filter(post -> post.getDate()
                .isAfter(twoWeeksAgo))
                .toList();

        //Exception. No posts from the last two weeks
        if(followedPostsfromTwoWeeksAgo.isEmpty()) throw new NotFoundException("There are no posts from two weeks ago");

        //Sort by date. Ascending or Descending
        if(order.isEmpty() || order.get().equals("date_desc")){
            return new ResponseFollowedPostsDTO(userId, followedPostsfromTwoWeeksAgo.stream().sorted(Comparator.comparing(Post::getDate).reversed()).map(post -> utilities.convertValue(post, PostDTO.class)).toList());
        } else if (order.get().equals("date_asc")) {
            return new ResponseFollowedPostsDTO(userId, followedPostsfromTwoWeeksAgo.stream().sorted(Comparator.comparing(Post::getDate)).map(post -> utilities.convertValue(post, PostDTO.class)).toList());
        } else {
            throw new BadRequestException("That's not a valid order criteria");
        }
    }
}
