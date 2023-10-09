package com.example.eproject4.Service;


import com.example.eproject4.Entity.Role;
import com.example.eproject4.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleService {
    private final RoleRepository roleRepository;
    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid role ID: " + id));
    }

    public void updateRole(Long id, Role updatedRole) {
        Role role = getRoleById(id);
        role.setName(updatedRole.getName());
        roleRepository.save(role);
    }
}