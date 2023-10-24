package com.example.eproject4.Controller.auth;

import com.example.eproject4.DTO.UserRegistrationDto;
import com.example.eproject4.Entity.Role;
import com.example.eproject4.Entity.User;
import com.example.eproject4.Repository.RoleRepository;
import com.example.eproject4.Repository.UserRepository;
import com.example.eproject4.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/adminoruser")
public class RouterAdminOrUserController {

    private RoleRepository roleRepository;
    @Autowired
    public RouterAdminOrUserController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping()
    public String router(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Nếu chưa đăng nhập, chuyển hướng đến trang admin/login
            return "redirect:/admin/login";
        }
        model.addAttribute("loggedInUser", loggedInUser);

        String firstRoleName = roleRepository.findFirstRoleNameByUsername(loggedInUser.getUsername());
        //List<Role> roles = (List<Role>) loggedInUser.getRoles();
        // Sử dụng phương thức getRoles trong User Entity

        model.addAttribute("roles", firstRoleName);
        if (Objects.equals(firstRoleName, "ROLE_ADMIN")){
            return "redirect:/admin";
        }else {
            return "redirect:/home";
        }
    }
}
