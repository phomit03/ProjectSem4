package com.example.eproject4.Controller.user;

import com.example.eproject4.DTO.Response.PlayerDTO;
import com.example.eproject4.DTO.Response.TeamDTO;
import com.example.eproject4.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamUserController {
    @Autowired
    private TeamService teamService;

    @RequestMapping("")
    public String teams(Model model) {
        model.addAttribute("overlay_title", "Teams");
        model.addAttribute("title", "Teams");
        model.addAttribute("description", "List of competing teams of the season");

        List<TeamDTO> teams = teamService.getAllTeams();
        model.addAttribute("teams", teams);

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
