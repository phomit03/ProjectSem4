package com.example.eproject4.Service;


import com.example.eproject4.Entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    Role getRoleById(Long id);

    Role saveRole(Role role);

    void deleteRole(Role roleId);
}