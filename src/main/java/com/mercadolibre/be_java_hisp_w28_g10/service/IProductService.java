package com.mercadolibre.be_java_hisp_w28_g10.service;

import com.mercadolibre.be_java_hisp_w28_g10.dto.PostDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.ProductDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.ResponseFollowedPostsDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponsePostNoPromoDTO;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    public ResponseFollowedPostsDTO getLastFollowedPosts(Integer userId, Optional<String> order);

    List<ProductDTO> getAllProducts();

    PostDTO addPromoPost(PostDTO promoPost);

    List<PostDTO> getAllPost();

    ResponsePostNoPromoDTO addPost(PostDTO post);
}
