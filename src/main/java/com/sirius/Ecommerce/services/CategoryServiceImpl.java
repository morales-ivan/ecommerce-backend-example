package com.sirius.Ecommerce.services;

import com.sirius.Ecommerce.model.Category;
import com.sirius.Ecommerce.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) { this.categoryRepository = categoryRepository; }

    @Override
    public List<Category> getCategories() {
        // return categories.stream().map(CategoryDTO::fromCategory).collect(Collectors.toList());
        return new ArrayList<>(categoryRepository.findAll());
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    @Override
    public Category insert(Category requestCategory) {
        Category category = new Category();
        category.setName(requestCategory.getName());
        category.setDescription(requestCategory.getDescription());
//    public Category insert(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Long id, Category category) {
        Category categoryFromDb = categoryRepository.findById(id).get();
        System.out.println(categoryFromDb);
        categoryFromDb.setName(category.getName());
        categoryFromDb.setDescription(category.getDescription());
        categoryRepository.save(categoryFromDb);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
