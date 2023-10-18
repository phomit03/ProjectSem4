package com.example.eproject4.Controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/info")
public class InfoUserController {
    @RequestMapping("")
    public String feedback(Model model) {
        model.addAttribute("overlay_title", "Info Account");
        model.addAttribute("title", "Feedback");
        model.addAttribute("description", "Please give us your contributions");

        return "customer_info_account";
    }
}
