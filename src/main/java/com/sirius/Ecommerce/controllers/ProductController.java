package com.sirius.Ecommerce.controllers;


import com.sirius.Ecommerce.model.product.ProductCreationDTO;
import com.sirius.Ecommerce.model.product.ProductListingDTO;
import com.sirius.Ecommerce.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) { this.productService = productService; }

    @GetMapping
    public List<ProductListingDTO> getAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{pageSize}/{pageNumber}")
    public List<ProductListingDTO> getPaginatedProducts(@PathVariable Integer pageSize, @PathVariable Integer pageNumber) {
        return productService.getPaginatedProducts(pageSize, pageNumber);
    }

    @GetMapping("/{productId}")
    public ProductListingDTO getProduct(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PostMapping
    public ProductListingDTO saveProduct(@RequestBody ProductCreationDTO productCreationDto) {
        return productService.save(productCreationDto);
    }

    @PutMapping("/{productId}")
    public ProductListingDTO updateProduct(@PathVariable Long productId, @RequestBody ProductListingDTO productListingDto) {
        productService.updateProduct(productId, productListingDto);
        return productService.getProductById(productId);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductListingDTO> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}