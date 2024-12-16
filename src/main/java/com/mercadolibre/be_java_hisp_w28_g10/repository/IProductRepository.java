package com.mercadolibre.be_java_hisp_w28_g10.repository;

import com.mercadolibre.be_java_hisp_w28_g10.model.Post;
import com.mercadolibre.be_java_hisp_w28_g10.model.Product;

import java.util.List;

public interface IProductRepository {
    List<Product> findAll();

    boolean savePromoPost(Post post);

    List<Post> findAllPost();
}
