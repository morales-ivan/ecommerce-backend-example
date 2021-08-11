package com.sirius.Ecommerce.services;

import com.sirius.Ecommerce.model.category.Category;
import com.sirius.Ecommerce.model.category.CategoryCreationDTO;
import com.sirius.Ecommerce.model.category.CategoryListingDTO;
import com.sirius.Ecommerce.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) { this.categoryRepository = categoryRepository; }

    @Override
    public List<CategoryListingDTO> getCategories() {
        // return categories.stream().map(CategoryDTO::fromCategory).collect(Collectors.toList());
        List<Category> categories = new ArrayList<>((Collection<? extends Category>) categoryRepository.findAll());
        return categories.stream().map(CategoryListingDTO::fromCategory).collect(Collectors.toList());
    }

    @Override
    public CategoryListingDTO getCategoryById(Long id) {
        return CategoryListingDTO.fromCategory(categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found")));
    }

    @Override
    public CategoryListingDTO save(CategoryCreationDTO requestCategory) {
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
        categoryRepository.deleteById(id);
    }
}
