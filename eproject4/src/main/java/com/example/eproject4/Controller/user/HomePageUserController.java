package com.example.eproject4.Controller.user;

import com.example.eproject4.DTO.Response.MatchDTO;
import com.example.eproject4.DTO.Response.MatchDetailEventDTO;
import com.example.eproject4.DTO.Response.PlayerDTO;
import com.example.eproject4.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("")
public class HomePageUserController {
    @Autowired
    private  MatchService matchService;
    @Autowired
    private  TeamService teamService;
    @Autowired
    private  StadiumService stadiumService;
    @Autowired
    private  MatchDetailService matchDetailService;
    @Autowired
    private  PlayerService playerService;
    @Autowired
    private  MatchDetailEventService matchDetailEventService;

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home Page");
        model.addAttribute("overlay_title", "World Cup Event");
        model.addAttribute("description", "Welcome to the most exciting tournament on the planet ^.^");

        List<MatchDTO> nextMatchesOrLiveMatches = matchService.getNextMatchesOrLiveMatches();
        model.addAttribute("nextMatchesOrLiveMatches", nextMatchesOrLiveMatches);
        return "customer_homepage";
    }
    @RequestMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("title", "Home Page");
        model.addAttribute("overlay_title", "World Cup Event");
        model.addAttribute("description", "Welcome to the most exciting tournament on the planet ^.^");

        List<MatchDTO> nextMatchesOrLiveMatches = matchService.getNextMatchesOrLiveMatches();
        model.addAttribute("nextMatchesOrLiveMatches", nextMatchesOrLiveMatches);
        return "customer_homepage";
    }
}
