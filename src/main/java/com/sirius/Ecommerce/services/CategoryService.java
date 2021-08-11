package com.sirius.Ecommerce.services;

import com.sirius.Ecommerce.model.category.CategoryCreationDTO;
import com.sirius.Ecommerce.model.category.CategoryListingDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    List<CategoryListingDTO> getCategories();

    Page<CategoryListingDTO> getPaginatedCategories(Integer pageSize, Integer pageNumber);

    CategoryListingDTO getCategoryById(Long id);

    CategoryListingDTO save(CategoryCreationDTO categoryCreationDTO);

    void updateCategory(Long id, CategoryListingDTO categoryListingDTO);

    void deleteCategory(Long id);
}
