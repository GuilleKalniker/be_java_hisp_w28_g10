package com.mercadolibre.be_java_hisp_w28_g10.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.dto.PostDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.ProductDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponsePostNoPromoDTO;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IProductRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.IProductService;
import com.mercadolibre.be_java_hisp_w28_g10.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    Utilities utilities;

    @Override
    public List<ProductDTO> getAllProducts() {
        ObjectMapper mapper = new ObjectMapper();
        return productRepository.findAll().stream().map(p -> mapper.convertValue(p, ProductDTO.class)).toList();
    }
}
