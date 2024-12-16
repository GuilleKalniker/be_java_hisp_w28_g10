package com.mercadolibre.be_java_hisp_w28_g10.controller;

import com.mercadolibre.be_java_hisp_w28_g10.dto.PostDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.ProductDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponseMessageDTO;
import com.mercadolibre.be_java_hisp_w28_g10.dto.response.ResponsePostNoPromoDTO;
import com.mercadolibre.be_java_hisp_w28_g10.service.IProductService;
import com.mercadolibre.be_java_hisp_w28_g10.service.impl.UserServiceimpl;
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

    @PostMapping("post")
    public ResponseEntity<ResponsePostNoPromoDTO> addPost(@RequestBody PostDTO post) {
        return new ResponseEntity<>(productService.addPost(post), HttpStatus.OK);
    }

    @PostMapping("products/promo-post")
    public ResponseEntity<ResponseMessageDTO> addPromoPost(@RequestBody PostDTO promoPost) {
        return new ResponseEntity<>(new ResponseMessageDTO(productService.addPromoPost(promoPost)), HttpStatus.OK);
    }

    @GetMapping("post/getAll")
    public ResponseEntity<List<PostDTO>> getAllPost() {
        return new ResponseEntity<>(productService.getAllPost(), HttpStatus.OK);
    }
}
