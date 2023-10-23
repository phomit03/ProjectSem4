package com.example.eproject4.Controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profiles")
public class EditProfileController {
    @RequestMapping("")
    public String profiles(Model model) {
        model.addAttribute("overlay_title", "Edit Profile");
        model.addAttribute("title", "Edit Profile");
        model.addAttribute("description", "Please give us your contributions");

        return "customer_upload_profile";
    }
}
