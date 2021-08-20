package com.sirius.Ecommerce.services;

import com.sirius.Ecommerce.model.role.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getRoles();

    Optional<Role> getRoleById(Long roleId);

    Role save(Role role);

    void updateRole(Long roleId, Role role);

    void deleteRole(Long roleId);
}
