package com.mercadolibre.be_java_hisp_w28_g10.service;

import com.mercadolibre.be_java_hisp_w28_g10.dto.PostDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.ProductDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.ResponseFollowedProductsDTO;

import java.util.List;

public interface IProductService {
    public List<ProductDTO> getAllProducts();
    public ResponseFollowedProductsDTO getLastFollowedProducts(Integer userId);
}
