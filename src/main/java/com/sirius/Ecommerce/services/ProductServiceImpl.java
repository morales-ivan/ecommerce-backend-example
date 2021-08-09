package com.sirius.Ecommerce.services;

import com.sirius.Ecommerce.model.Product;
import com.sirius.Ecommerce.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) { this.productRepository = productRepository; }

    @Override
    public List<Product> getProducts() {
        // return products.stream().map(ProductDTO::fromProduct).collect(Collectors.toList());
        return new ArrayList<>(productRepository.findAll());
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Product not found"));
    }

    @Override
    public Product insert(Product requestProduct) {
        Product product = new Product();
        product.setName(requestProduct.getName());
        product.setDescription(requestProduct.getDescription());
//    public Product insert(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void updateProduct(Long id, Product product) {
        Product productFromDb = productRepository.findById(id).get();
        System.out.println(productFromDb);
        productFromDb.setName(product.getName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productRepository.save(productFromDb);
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}