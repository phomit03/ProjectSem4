package com.example.eproject4.Controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginUserController {
    @GetMapping("/login")
    public String showLoginForm(Model model) {
//        model.addAttribute("erroruser", "Sai tài khoản hoặc mật khẩu.");
        return "customer_login";
    }
    @GetMapping("/home")
    public String home(){
        return "customer_homepage";
    }


}
