package com.mercadolibre.be_java_hisp_w28_g10.service;

import com.mercadolibre.be_java_hisp_w28_g10.dto.PostDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.ProductDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponsePostNoPromoDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ProductsWithPromoDTO;

import java.util.List;

public interface IProductService {
    List<ProductDTO> getAllProducts();

    PostDTO addPromoPost(PostDTO promoPost);
    ProductsWithPromoDTO productsWithPromoDTO(int id);

    List<PostDTO> getAllPost();

    ResponsePostNoPromoDTO addPost(PostDTO post);
}
