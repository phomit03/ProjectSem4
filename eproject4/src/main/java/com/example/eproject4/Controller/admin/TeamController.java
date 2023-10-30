package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.Response.TeamDTO;
import com.example.eproject4.Entity.Team;
import com.example.eproject4.Entity.User;
import com.example.eproject4.Repository.TeamRepository;
import com.example.eproject4.Service.TeamConclusionService;
import com.example.eproject4.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TeamController {
    private final TeamService teamService;
    private final TeamRepository teamRepository;
    private TeamConclusionService teamConclusionService;

    @Autowired
    public TeamController(TeamService teamService,TeamRepository teamRepository,TeamConclusionService teamConclusionService) {
        this.teamService = teamService;
        this.teamRepository = teamRepository;
        this.teamConclusionService = teamConclusionService;
    }

    @GetMapping("/team/create")
    public String showCreateForm(Model model) {
        model.addAttribute("teamDTO", new TeamDTO());
        return "admin_team_create";
    }


    @PostMapping("/team/create/save")
    public String createTeam(@ModelAttribute TeamDTO teamDTO, @RequestParam("logo") MultipartFile logo, RedirectAttributes attributes) {
        try {
            teamService.createTeam(teamDTO, logo);
            teamConclusionService.create(teamDTO.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/team";
    }

    @GetMapping("/team/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
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
    public ResponseEntity<String> softDelete(@PathVariable Long id) {
        try {
            teamService.softDelete(id);
            return ResponseEntity.ok("Delete team successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
        }
    }


    //phan trang
    @GetMapping("/team")
    public String getAllTeams(Model model,
                             @RequestParam(name = "name", required = false) String name,
                             @RequestParam(name = "coach", required = false) String coach,
                             @RequestParam(name = "home_stadium", required = false) String home_stadium,
                             @RequestParam(name = "status", required = false) Integer status
    ) {
        model.addAttribute("title", "Teams");
        return findPaginated(1, model, name, coach,home_stadium,status);
    }

    @GetMapping("/team/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                Model model,
                                @RequestParam(name = "name", required = false) String name,
                                @RequestParam(name = "coach", required = false) String coach,
                                @RequestParam(name = "home_stadium", required = false) String home_stadium,
                                @RequestParam(name = "status", required = false) Integer status
    ) {
        int pageSize = 6;

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        List<Team> result = teamRepository.searchTeams(name, coach,home_stadium,status, pageable);
        Page<Team> page = new PageImpl<>(result, pageable,teamRepository.searchTeams1(name, coach,home_stadium,status).size());
        List<Team> teams = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("teams", teams);
        model.addAttribute("name", name);
        model.addAttribute("status", status);
        model.addAttribute("coach", coach);
        model.addAttribute("home_stadium", home_stadium);
        return "admin_team";
    }
}
