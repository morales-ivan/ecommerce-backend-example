package com.sirius.Ecommerce.services;

import com.sirius.Ecommerce.model.role.Role;
import com.sirius.Ecommerce.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getRoles() {
        return new ArrayList<>((Collection<? extends Role>) roleRepository.findAll());
    }

    @Override
    public Optional<Role> getRoleById(Long roleId) {
        return roleRepository.findById(roleId);
    }

    @Override
    public Role save(Role role) {
        // save role
        return null;
    }

    @Override
    public void updateRole(Long roleId, Role role) {
        // update role info
    }

    @Override
    public void deleteRole(Long roleId) {
        // delete role by id
    }
}
