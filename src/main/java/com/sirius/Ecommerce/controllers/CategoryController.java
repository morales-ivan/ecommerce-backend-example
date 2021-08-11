package com.sirius.Ecommerce.controllers;


import com.sirius.Ecommerce.model.category.Category;
import com.sirius.Ecommerce.model.category.CategoryCreationDTO;
import com.sirius.Ecommerce.model.category.CategoryListingDTO;
import com.sirius.Ecommerce.services.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) { this.categoryService = categoryService; }

    @GetMapping
    public List<CategoryListingDTO> getAllCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/{pageSize}/{pageNumber}")
    public Page<CategoryListingDTO> getPaginatedCategories(@PathVariable Integer pageSize, @PathVariable Integer pageNumber) {
        return categoryService.getPaginatedCategories(pageSize, pageNumber);
    }

    @GetMapping("/{categoryId}")
    public CategoryListingDTO getCategory(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @PostMapping
    public CategoryListingDTO saveCategory(@RequestBody CategoryCreationDTO categoryCreationDTO) {
        return categoryService.save(categoryCreationDTO);
    }

    @PutMapping("/{categoryId}")
    public CategoryListingDTO updateCategory(@PathVariable Long categoryId, @RequestBody CategoryListingDTO updatedCategoryListingDTO) {
        categoryService.updateCategory(categoryId, updatedCategoryListingDTO);
        return categoryService.getCategoryById(categoryId);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CategoryListingDTO> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}