package com.sirius.Ecommerce.services;

import com.sirius.Ecommerce.model.category.Category;
import com.sirius.Ecommerce.model.product.Product;
import com.sirius.Ecommerce.model.product.ProductCreationDTO;
import com.sirius.Ecommerce.model.product.ProductListingDTO;
import com.sirius.Ecommerce.repositories.CategoryRepository;
import com.sirius.Ecommerce.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductListingDTO> getProducts() {
        List<Product> products = new ArrayList<>((Collection<? extends Product>) productRepository.findAll());
        return products.stream().map(ProductListingDTO::fromProduct).collect(Collectors.toList());
    }

    @Override
    public Page<ProductListingDTO> getPaginatedProducts(Integer pageSize, Integer pageNumber) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Product> paginatedProducts = productRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return new PageImpl<>(paginatedProducts.getContent().stream()
                                    .map(ProductListingDTO::fromProduct).collect(Collectors.toList()),
                              pageRequest,
                              paginatedProducts.getTotalElements());
    }

    @Override
    public ProductListingDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Product not found"));
        return ProductListingDTO.fromProduct(product);
    }

    @Override
    public ProductListingDTO save(ProductCreationDTO requestProduct) {
        if (productRepository.existsByName(requestProduct.getName()))
            throw new IllegalArgumentException("Existing product name!");

        Set<Category> categories = requestProduct.getCategoryIds().stream()
                .map(categoryId -> categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new IllegalArgumentException("Category not found")))
                .collect(Collectors.toSet());

        Product product = new Product();
        product.setName(requestProduct.getName());
        product.setDescription(requestProduct.getDescription());
        product.setQuantity(requestProduct.getQuantity());
        product.setCategories(categories);

        return ProductListingDTO.fromProduct(productRepository.save(product));
    }

    @Override
    public void updateProduct(Long id, ProductListingDTO productListingDto) {
        Product productFromDb = productRepository.findById(id).get();
        System.out.println(productFromDb);
        productFromDb.setName(productListingDto.getName());
        productFromDb.setDescription(productListingDto.getDescription());
        productFromDb.setQuantity(productListingDto.getQuantity());

        Set<Category> categories = productListingDto.getCategoryIds().stream()
                .map(category -> categoryRepository.findById(category).orElseThrow(() -> new IllegalArgumentException("Category not found")))
                .collect(Collectors.toSet());

        productFromDb.setCategories(categories);
        productRepository.save(productFromDb);
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}