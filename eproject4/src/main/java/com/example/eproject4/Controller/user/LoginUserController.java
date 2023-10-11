package com.example.eproject4.Controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/login")
public class LoginUserController {
    @RequestMapping("")
    public String login(Model model) {
        model.addAttribute("overlay_title", "Login");
        model.addAttribute("title", "Login");
        model.addAttribute("description", "day la mo ta duoi title");

        return "customer_login";
    }
}
