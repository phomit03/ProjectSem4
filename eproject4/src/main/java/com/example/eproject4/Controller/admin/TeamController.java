package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.Request.TeamRequest;
import com.example.eproject4.DTO.TeamDTO;
import com.example.eproject4.Entity.Team;
import com.example.eproject4.Repository.TeamRepository;
import com.example.eproject4.Service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class TeamController {
    private final TeamService teamService;
    private final TeamRepository teamRepository;

    public TeamController(TeamService teamService, TeamRepository teamRepository) {
        this.teamService = teamService;
        this.teamRepository = teamRepository;
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

    @GetMapping("/team/create")
    public String showCreateForm(Model model) {
        TeamRequest teamRequest = new TeamRequest();
        model.addAttribute("teamRequest", teamRequest);
        model.addAttribute("actionUrl", "/admin/team/create/save");
        return "admin_team_create";
    }

    @GetMapping("/team/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model)  {
        Optional<Team> team = teamRepository.findById(id);
        if (team == null) {
            return "redirect:/admin/team";
        }

        model.addAttribute("team", team);
        model.addAttribute("actionUrl", "/admin/team/update/" + id);
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

    @GetMapping("/team/update/{id}")
    public String getTeamById(@PathVariable Long id) {
        TeamDTO teamDTO = teamService.getTeamById(id);
        return null;
    }

    @GetMapping("/team/delete/{id}")
    public ResponseEntity<String> deleteTeamById(@PathVariable Long id) {
        try {
            teamRepository.deleteById(id);
            return ResponseEntity.ok("Delete team successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
        }
    }
}
