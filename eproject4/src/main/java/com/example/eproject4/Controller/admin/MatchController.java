package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.Request.MatchRequest;
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
        List<MatchRequest> allMatches = matchService.getAllMatches();

        int totalItems = allMatches.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        List<MatchRequest> matches = allMatches.subList((page - 1) * pageSize, Math.min(page * pageSize, totalItems));

        model.addAttribute("matches", matches);
        model.addAttribute("title", "Matches");
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "admin_match";
    }

    @GetMapping("match/new")
    public String create(Model model) {
        MatchRequest matchRequest = new MatchRequest();
        model.addAttribute("match", matchRequest);

        return "admin_match_create";
    }

    @PostMapping("match/new")
    public String create(@ModelAttribute MatchRequest matchRequest, RedirectAttributes redirectAttributes) {
        MatchRequest createdMatch = matchService.createMatch(matchRequest);

        if (createdMatch != null) {
            redirectAttributes.addFlashAttribute("successMessage", "Match created successfully!");
            return "redirect:/admin/matches";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create match.");
            return "redirect:/admin/matches/new";
        }
    }



}
