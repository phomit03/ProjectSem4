package com.example.eproject4.Controller.user;

import com.example.eproject4.DTO.Response.MatchDTO;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/matches")
public class MatchUserController {
    private final MatchService matchService;

    @Autowired
    public MatchUserController(MatchService matchService) {
        this.matchService = matchService;
    }

    @RequestMapping("")
    public String matches(Model model) {
        List<Match> latestFinishedMatches = matchService.findLatestFinishedMatches();
        List<Match> findAllFinishedMatches = matchService.findAllFinishedMatches();
        List<Match> findNextMatch = matchService.getFindNextMatch();

        model.addAttribute("overlay_title", "Matches");
        model.addAttribute("title", "Matches");
        model.addAttribute("description", "Follow the schedule and results of the season's matches");
        model.addAttribute("latestFinishedMatches", latestFinishedMatches);
        model.addAttribute("findAllFinishedMatches", findAllFinishedMatches);
        model.addAttribute("findNextMatch", findNextMatch);

        return "customer_matches";
    }
}
