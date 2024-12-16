package com.mercadolibre.be_java_hisp_w28_g10.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.dto.*;
import com.mercadolibre.be_java_hisp_w28_g10.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.model.Post;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IProductRepository;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.IProductService;
import com.mercadolibre.be_java_hisp_w28_g10.service.IUserService;
import com.mercadolibre.be_java_hisp_w28_g10.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        ObjectMapper mapper = new ObjectMapper();
        return productRepository.findAll().stream().map(p -> mapper.convertValue(p, ProductDTO.class)).toList();
    }

    @Override
    public ResponseFollowedProductsDTO getLastFollowedProducts(Integer userId) {

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

        return new ResponseFollowedProductsDTO(userId, postDTOSfromTwoWeeksAgo);
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
