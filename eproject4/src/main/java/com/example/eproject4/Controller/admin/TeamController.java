package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.Response.TeamDTO;
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
        model.addAttribute("teamDTO", new TeamDTO());
        return "admin_team_create";
    }


    @PostMapping("/team/create/save")
    public String createTeam(@ModelAttribute TeamDTO teamDTO, @RequestParam("logo") MultipartFile logo, RedirectAttributes attributes){
        try {
            teamService.createTeam(teamDTO, logo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/team";
    }

    @GetMapping("/team/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model)  {
        TeamDTO team = teamService.getTeamById(id);
        if (team == null) {
            return "redirect:/admin/team";
        }

        model.addAttribute("teamDTO", team);
        return "admin_team_update";
    }

    @PostMapping("/team/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("teamDTO") TeamDTO teamDTO, @RequestParam(value = "logo", required = false) MultipartFile logo, RedirectAttributes attributes) {
        try {
            teamService.update(teamDTO, logo);
            attributes.addFlashAttribute("success", "Update Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }
        return "redirect:/admin/team";
    }

    @GetMapping("/team/delete/{id}")
    public ResponseEntity<String> deleteTeamById(@PathVariable Long id) {
        try {
            teamService.delete(id);
            return ResponseEntity.ok("Delete team successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
        }
    }
}
