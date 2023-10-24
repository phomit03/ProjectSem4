package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.Response.PlayerDTO;
import com.example.eproject4.DTO.Response.TeamDTO;
import com.example.eproject4.Entity.Player;
import com.example.eproject4.Service.PlayerService;
import com.example.eproject4.Service.TeamService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class PlayerController {
    private final PlayerService playerService;
    private final TeamService teamService;

    public PlayerController(PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;
    }

    @GetMapping("/player")
    public String player(Model model) {
        List<PlayerDTO> playerDTOList = playerService.getAllPlayers();

        model.addAttribute("players", playerDTOList);
        model.addAttribute("title", "Players");
        return findPaginated(1, model);

    }

    @GetMapping("/player/create")
    public String showCreateForm(Model model) {
        model.addAttribute("playerDTO", new PlayerDTO());
        List<TeamDTO> teams = teamService.getAllTeams();
        model.addAttribute("teams", teams);
        return "admin_player_create";
    }

    @PostMapping("/player/create/save")
    public String create(@ModelAttribute PlayerDTO playerDTO, @RequestParam("img") MultipartFile img, RedirectAttributes redirectAttributes) {
        try {
            playerService.create(playerDTO, img);
            redirectAttributes.addFlashAttribute("success", "Create successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to create!");
        }
        return "redirect:/admin/player";
    }

    @GetMapping("/player/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        PlayerDTO playerDTO = playerService.getPlayerById(id);
        if (playerDTO == null) {
            return "redirect:/admin/player";
        }

        List<TeamDTO> teams = teamService.getAllTeams();
        model.addAttribute("teams", teams);
        model.addAttribute("playerDTO", playerDTO);
        return "admin_player_update";
    }

    @PostMapping("/player/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("playerDTO") PlayerDTO playerDTO, @RequestParam(value = "img", required = false) MultipartFile img, RedirectAttributes redirectAttributes) {
        try {
            playerService.update(playerDTO, img);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to update!");
        }
        return "redirect:/admin/player";
    }

    @GetMapping("/player/delete/{id}")
    public ResponseEntity<String> softDelete(@PathVariable Long id) {
        try {
            playerService.softDelete(id);
            return ResponseEntity.ok("Delete player successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
        }
    }

    //phan trang
    @GetMapping("/player/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                Model model) {
        int pageSize = 8;
        Page<Player> page = playerService.findPaginated(pageNo, pageSize);
        List<Player> players = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("players", players);
        return "admin_player";
    }
}
