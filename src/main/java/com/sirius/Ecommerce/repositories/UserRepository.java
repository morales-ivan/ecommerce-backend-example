package com.sirius.Ecommerce.repositories;

import com.sirius.Ecommerce.model.product.Product;
import com.sirius.Ecommerce.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Page<User> findAll(Pageable pageRequest);

    boolean existsByUsername(String username);

}
