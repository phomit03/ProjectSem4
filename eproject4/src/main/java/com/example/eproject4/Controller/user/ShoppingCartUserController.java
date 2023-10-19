package com.example.eproject4.Controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopping_cart")
public class ShoppingCartUserController {
    @RequestMapping("")
    public String shopping_cart_view(Model model) {
        model.addAttribute("overlay_title", "ShoppingCart");
        model.addAttribute("title", "ShoppingCart");
        model.addAttribute("description", "Matches have and will take place");

        return "shopping_cart";
    }
}
