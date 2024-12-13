package com.mercadolibre.be_java_hisp_w28_g10.service;

import com.mercadolibre.be_java_hisp_w28_g10.dto.ProductDTO;

import java.util.List;

public interface IProductService {
    public List<ProductDTO> getAllProducts();

}
