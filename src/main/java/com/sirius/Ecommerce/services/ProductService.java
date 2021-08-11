package com.sirius.Ecommerce.services;

import com.sirius.Ecommerce.model.product.ProductCreationDTO;
import com.sirius.Ecommerce.model.product.ProductListingDTO;

import java.util.List;

public interface ProductService {
    List<ProductListingDTO> getProducts();

    ProductListingDTO getProductById(Long productId);

    ProductListingDTO save(ProductCreationDTO productCreationDto);

    void updateProduct(Long productId, ProductListingDTO productListingDto);

    void deleteProduct(Long productId);
}
