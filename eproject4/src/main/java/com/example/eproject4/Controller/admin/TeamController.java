package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.Request.TeamRequest;
import com.example.eproject4.DTO.TeamDTO;
import com.example.eproject4.Service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @RequestMapping("/team")
    public String match(Model model, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 20;
        List<TeamDTO> allMatches = teamService.getAllTeams();

        int totalItems = allMatches.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        List<TeamDTO> teams = allMatches.subList((page - 1) * pageSize, Math.min(page * pageSize, totalItems));

        model.addAttribute("teams", teams);
        model.addAttribute("title", "Teams");
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "admin_team";
    }

    @RequestMapping("/team/create")
    public String createForm(Model model) {
        return "admin_team_create";
    }

    @PostMapping("/team/create/save")
    public String createTeam(@ModelAttribute TeamRequest teamRequest, @RequestParam("logo") MultipartFile logo, RedirectAttributes attributes){
        try {
            teamService.createTeam(teamRequest, logo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/team";
    }

    @GetMapping("/team/read/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id) {
        TeamDTO teamDTO = teamService.getTeamById(id);
        return ResponseEntity.ok(teamDTO);
    }
}
