package com.example.eproject4.Controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/matches")
public class MatchUserController {
    @RequestMapping("")
    public String matches(Model model) {
        model.addAttribute("overlay_title", "Matches");
        model.addAttribute("title", "Matches");
        model.addAttribute("description", "Matches have and will take place");

        return "customer_matches";
    }
}
