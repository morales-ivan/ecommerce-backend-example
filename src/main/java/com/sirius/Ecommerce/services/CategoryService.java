package com.sirius.Ecommerce.services;

import com.sirius.Ecommerce.model.category.CategoryCreationDTO;
import com.sirius.Ecommerce.model.category.CategoryListingDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryListingDTO> getCategories();

    List<CategoryListingDTO> getPaginatedCategories(Integer pageSize, Integer pageNumber);

    CategoryListingDTO getCategoryById(Long id);

    CategoryListingDTO save(CategoryCreationDTO categoryCreationDTO);

    void updateCategory(Long id, CategoryListingDTO categoryListingDTO);

    void deleteCategory(Long id);
}
