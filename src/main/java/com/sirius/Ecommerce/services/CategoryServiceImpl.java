package com.sirius.Ecommerce.services;

import com.sirius.Ecommerce.model.category.Category;
import com.sirius.Ecommerce.model.category.CategoryCreationDTO;
import com.sirius.Ecommerce.model.category.CategoryListingDTO;
import com.sirius.Ecommerce.model.product.Product;
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
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<CategoryListingDTO> getCategories() {
        // return categories.stream().map(CategoryDTO::fromCategory).collect(Collectors.toList());
        List<Category> categories = new ArrayList<>((Collection<? extends Category>) categoryRepository.findAll());
        return categories.stream().map(CategoryListingDTO::fromCategory).collect(Collectors.toList());
    }

    @Override
    public Page<CategoryListingDTO> getPaginatedCategories(Integer pageNumber, Integer pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Category> paginatedCategories = categoryRepository.findAll(pageRequest);
        return new PageImpl<>(paginatedCategories.getContent().stream()
                                    .map(CategoryListingDTO::fromCategory).collect(Collectors.toList()),
                              pageRequest,
                              paginatedCategories.getTotalElements());
    }

    @Override
    public CategoryListingDTO getCategoryById(Long id) {
        return CategoryListingDTO.fromCategory(categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found")));
    }

    @Override
    public CategoryListingDTO save(CategoryCreationDTO requestCategory) {
        if (categoryRepository.existsByName(requestCategory.getName()))
            throw new IllegalArgumentException("Existing category name!");

        Category category = new Category();
        category.setName(requestCategory.getName());
        category.setDescription(requestCategory.getDescription());
        return CategoryListingDTO.fromCategory(categoryRepository.save(category));
    }

    @Override
    public void updateCategory(Long id, CategoryListingDTO categoryListingDTO) {
        Category categoryFromDb = categoryRepository.findById(id).get();
        System.out.println(categoryFromDb);
        categoryFromDb.setName(categoryListingDTO.getName());
        categoryFromDb.setDescription(categoryListingDTO.getDescription());
        categoryRepository.save(categoryFromDb);
    }

    @Override
    public void deleteCategory(Long id) {
        // Category category = categoryRepository.findById(id).get();
        // List<Product> products = productRepository.findAllByCategoriesContains(category);
        List<Product> products = productRepository.findAllWithCategoryId(id);

        Category deprecatedCategory = categoryRepository.findById(id).get();
        updateProducts(products, deprecatedCategory);

        categoryRepository.deleteById(id);
    }
    
    private void updateProducts(List<Product> products, Category deprecatedCategory) {
        for (Product product:
                products) {
            Set<Category> productCategories = product.getCategories();

            productCategories.remove(deprecatedCategory);

            product.setCategories(productCategories);

            productRepository.save(product);
            }
        }
    }
