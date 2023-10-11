package com.example.eproject4.Controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/register")
public class RegisterUserController {
    @RequestMapping("")
    public String register(Model model) {
        model.addAttribute("overlay_title", "Register");
        model.addAttribute("title", "Register");
        model.addAttribute("description", "day la mo ta duoi title");

        return "customer_register";
    }
}
