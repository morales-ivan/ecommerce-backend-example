package com.sirius.Ecommerce.repositories;

import com.sirius.Ecommerce.model.role.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
