package com.example.eproject4.Controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teams")
public class TeamUserController {
    @RequestMapping("")
    public String teams(Model model) {
        model.addAttribute("overlay_title", "Teams");
        model.addAttribute("title", "Teams");
        model.addAttribute("description", "List of competing teams of the season");

        return "customer_teams";
    }
    @RequestMapping("/detail")
    public String teamDetail(Model model) {
        model.addAttribute("title", "Teams Detail");
        model.addAttribute("overlay_title", "Day la ten doi bong");
        model.addAttribute("description", "Mo ta doi bong");

        return "customer_team_detail";
    }
}
