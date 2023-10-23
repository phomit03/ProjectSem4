package com.example.eproject4.Controller.admin;

import com.example.eproject4.Entity.Role;
import com.example.eproject4.Service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String getAllRoles(Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("title", "Roles");
        model.addAttribute("roles", roles);
        return "admin_role_list";
    }

//    @GetMapping("/delete/{id}")
//    public String deleteRole(@PathVariable("id") Long id) {
//        roleService.deleteRole(id);
//        return "redirect:/roles";
//    }

    @GetMapping("/{id}/update")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Role role = roleService.getRoleById(id);
        model.addAttribute("role", role);
        return "admin_role_update";
    }

    @PostMapping("/{id}/update")
    public String updateRole(@PathVariable("id") Long id, @ModelAttribute("role") Role updatedRole) {
        roleService.updateRole(id, updatedRole);
        return "redirect:/admin/role";
    }
}