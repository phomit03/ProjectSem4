package com.example.eproject4.Controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/checkout_payment")
public class CheckOutUserController {
    @GetMapping
    public String checkout(Model model) {
        model.addAttribute("overlay_title", "Payment");
        model.addAttribute("title", "Payment");
        model.addAttribute("description", "Matches have and will take place");

        return "checkout_payment";
    }
}
