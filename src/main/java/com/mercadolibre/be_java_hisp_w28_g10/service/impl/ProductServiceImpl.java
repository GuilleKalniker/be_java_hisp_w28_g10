package com.mercadolibre.be_java_hisp_w28_g10.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w28_g10.dto.ProductDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ProductsWithPromoDTO;
import com.mercadolibre.be_java_hisp_w28_g10.model.Post;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IProductRepository;
import com.mercadolibre.be_java_hisp_w28_g10.repository.IUserRepository;
import com.mercadolibre.be_java_hisp_w28_g10.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<ProductDTO> getAllProducts() {
        ObjectMapper mapper = new ObjectMapper();
        return productRepository.findAll().stream().map(p -> mapper.convertValue(p, ProductDTO.class)).toList();
    }
    @Override
    public ProductsWithPromoDTO productsWithPromoDTO(int id){
        User user = userRepository.getUserById(id);
        int productPromo = Math.toIntExact(user.getPostList().stream().filter(Post::isHasPromo).count());
        return new ProductsWithPromoDTO(id, user.getName(),productPromo);
    }
}
