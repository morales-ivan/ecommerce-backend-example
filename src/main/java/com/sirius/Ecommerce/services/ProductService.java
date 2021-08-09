package com.sirius.Ecommerce.services;

import com.sirius.Ecommerce.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();

    Product getProductById(Long productId);

    Product insert(Product product);

    void updateProduct(Long productId, Product product);

    void deleteProduct(Long productId);
}
