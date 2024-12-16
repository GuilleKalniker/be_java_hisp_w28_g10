package com.mercadolibre.be_java_hisp_w28_g10.controller;

import com.mercadolibre.be_java_hisp_w28_g10.dto.ProductDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.ResponseFollowedPostsDTO;
import com.mercadolibre.be_java_hisp_w28_g10.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping("getAll")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping("products/promo-post")
    public void addPromoPost(){

    }

    @GetMapping("followed/{userId}/list")
    public ResponseEntity<ResponseFollowedPostsDTO> getAllProducts(@PathVariable int userId) {
        return new ResponseEntity<>(productService.getLastFollowedPosts(userId), HttpStatus.OK);
    }
}
