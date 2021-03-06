package com.sirius.Ecommerce.repositories;

import com.sirius.Ecommerce.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Page<User> findAll(Pageable pageRequest);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

}
