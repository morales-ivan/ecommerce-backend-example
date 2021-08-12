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

    List<Product> findAllByCategoriesContains(Category category);
}
