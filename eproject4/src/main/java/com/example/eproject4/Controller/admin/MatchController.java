package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.Request.MatchRequest;
import com.example.eproject4.DTO.Response.MatchDTO;
import com.example.eproject4.Service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/match/new")
    public String create(Model model) {
        MatchRequest matchRequest = new MatchRequest();
        model.addAttribute("match", matchRequest);

        return "admin_match_create";
    }

    @PostMapping("/match/new/save")
    public String create(@ModelAttribute MatchRequest matchRequest){
        try {
            matchService.createMatch(matchRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/matches";
    }

    @GetMapping("/match/edit/{id}")
    public String edit(@PathVariable Long id, Model model)  {
        MatchDTO match = matchService.getMatchById(id);
        if (match == null) {
            return "redirect:/admin/matches";
        }

        model.addAttribute("matchDTO", match);
        return "admin_match_edit";
    }

    @PostMapping("/match/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("matchRequest") MatchRequest matchRequest,
                         RedirectAttributes attributes) {
        try {
            matchService.updateMatch(matchRequest);
            attributes.addFlashAttribute("success", "Update Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }
        return "redirect:/admin/matches";
    }

    @GetMapping("/match/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            matchService.deleteMatch(id);
            return ResponseEntity.ok("Delete successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
        }
    }
}
