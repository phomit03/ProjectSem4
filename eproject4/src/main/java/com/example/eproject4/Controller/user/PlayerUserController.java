package com.example.eproject4.Controller.user;

import com.example.eproject4.DTO.Response.MatchDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/players")
public class PlayerUserController {
    @RequestMapping("")
    public String players(Model model) {
        model.addAttribute("overlay_title", "Players");
        model.addAttribute("title", "Players");
        model.addAttribute("description", "day la mo ta duoi title");

        return "customer_players";
    }
}
