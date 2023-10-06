package com.example.eproject4.Service;


import com.example.eproject4.Entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    void deleteRole(Long id);
    Role getRoleById(Long id);
    void updateRole(Long id, Role updatedRole);
}