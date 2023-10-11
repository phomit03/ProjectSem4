package com.example.eproject4.Controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomePageUserController {
    @RequestMapping("")
    public String home(Model model) {
        model.addAttribute("title", "Home Page");
        model.addAttribute("overlay_title", "World Cup Event");
        model.addAttribute("description", "Welcome to the most exciting tournament on the planet ^.^");

        return "customer_homepage";
    }
}
