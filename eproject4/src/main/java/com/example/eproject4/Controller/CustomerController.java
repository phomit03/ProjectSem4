package com.example.eproject4.Controller;

import com.example.eproject4.DTO.Response.AreaDTO;
import com.example.eproject4.DTO.Response.NewDTO;
import com.example.eproject4.DTO.Response.TeamDTO;
import com.example.eproject4.DTO.Response.UserDTO;
import com.example.eproject4.Entity.User;
import com.example.eproject4.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account")
public class CustomerController {
    private UserService userService;
    @Autowired
    public CustomerController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/info/{id}")
    public String accountInfo(@PathVariable Long id, Model model) {
        model.addAttribute("overlay_title", "Account Info");
        model.addAttribute("title", "Account Info");
        model.addAttribute("description", "This is your account information.");

        User user = userService.getUserById(id);
        model.addAttribute("user", user);

        return "customer_info_account";
    }

    @PostMapping("update/info/{id}")
    public String updateInfo(@PathVariable Long id, @ModelAttribute("user") User user, RedirectAttributes attributes) {
        try {
            userService.updateUser(id, user);
            attributes.addFlashAttribute("success", "Update successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }

        return "redirect:/account/info/{id}";
    }
}
