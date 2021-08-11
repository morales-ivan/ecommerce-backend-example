package com.sirius.Ecommerce.repositories;

import com.sirius.Ecommerce.model.product.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface ProductRepository extends CrudRepository<Product, Long> {
}
