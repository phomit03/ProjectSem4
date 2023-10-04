package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.MatchDTO;
import com.example.eproject4.Service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class MatchController {
    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @RequestMapping("/matches")
    public String matches(Model model, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 20;
        List<MatchDTO> allMatches = matchService.getAllMatches();

        int totalItems = allMatches.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        List<MatchDTO> matches = allMatches.subList((page - 1) * pageSize, Math.min(page * pageSize, totalItems));

        model.addAttribute("matches", matches);
        model.addAttribute("title", "Matches");
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "admin_match";
    }

    @GetMapping("match/new")
    public String create(Model model) {
        MatchDTO matchDTO = new MatchDTO();
        model.addAttribute("match", matchDTO);

        return "admin_create_match";
    }

    @PostMapping("match/new")
    public String create(@ModelAttribute MatchDTO matchDTO, RedirectAttributes redirectAttributes) {
        MatchDTO createdMatch = matchService.createMatch(matchDTO);

        if (createdMatch != null) {
            redirectAttributes.addFlashAttribute("successMessage", "Match created successfully!");
            return "redirect:/admin/matches";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create match.");
            return "redirect:/admin/matches/new";
        }
    }
    @GetMapping("/match/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        MatchDTO matchDTO = matchService.getMatchById(id);

        if (matchDTO != null) {
            model.addAttribute("match", matchDTO);
            model.addAttribute("title", "Match Edit");
            return "admin_edit_match";
        } else {
            return "redirect:/admin/matches";
        }
    }

    @PutMapping("/match/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute MatchDTO matchDTO, RedirectAttributes redirectAttributes) {
        MatchDTO updatedMatch = matchService.updateMatch(id, matchDTO);

        if (updatedMatch != null) {
            redirectAttributes.addFlashAttribute("successMessage", "Match updated successfully!");
            return "redirect:/admin/match/{id}";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update match.");
            return "redirect:/admin/match/{id}/edit";
        }
    }


}
