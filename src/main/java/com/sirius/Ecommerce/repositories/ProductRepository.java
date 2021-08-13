package com.sirius.Ecommerce.repositories;

import com.sirius.Ecommerce.model.category.Category;
import com.sirius.Ecommerce.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    Page<Product> findAll(Pageable pageRequest);

    boolean existsByName(String name);

    @Query(value = "SELECT * FROM product potentialProduct WHERE ?1 IN (SELECT relation.categories_id from product_categories relation where potentialProduct.id=relation.product_id);",
            nativeQuery = true)
    List<Product> findAllWithCategoryId(Long categoryId);

    // List<Product> findAllByCategoriesContains(Category category);
}
