package com.example.eproject4.Controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feedback")
public class FeedbackUserController {
    @RequestMapping("")
    public String feedback(Model model) {
        model.addAttribute("overlay_title", "Feedback");
        model.addAttribute("title", "Feedback");
        model.addAttribute("description", "Please give us your contributions");

        return "customer_feedbacks";
    }
}
