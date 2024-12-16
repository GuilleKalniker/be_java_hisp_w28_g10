package com.mercadolibre.be_java_hisp_w28_g10.service;

import com.mercadolibre.be_java_hisp_w28_g10.dto.ProductDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ProductsWithPromoDTO;

import java.util.List;

public interface IProductService {
    public List<ProductDTO> getAllProducts();
    public ProductsWithPromoDTO productsWithPromoDTO(int id);

}
