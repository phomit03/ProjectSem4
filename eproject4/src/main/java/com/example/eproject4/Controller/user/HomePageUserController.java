package com.example.eproject4.Controller.user;

import com.example.eproject4.DTO.Response.MatchDTO;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Entity.New;
import com.example.eproject4.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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
    @Autowired
    private  NewService newService;

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home Page");
        model.addAttribute("overlay_title", "World Cup Event");
        model.addAttribute("description", "Welcome to the most exciting tournament on the planet ^.^");

        List<MatchDTO> nextMatchesOrLiveMatches = matchService.getNextMatchesOrLiveMatches(3);
        model.addAttribute("nextMatchesOrLiveMatches", nextMatchesOrLiveMatches);

        //last-news
        List<New> latestNews = newService.getLatestNews();
        model.addAttribute("latestNews", latestNews);

        return "customer_homepage";
    }
    @RequestMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("title", "Home Page");
        model.addAttribute("overlay_title", "Premier League 2023");
        model.addAttribute("description", "Welcome to the most exciting tournament on the planet");

        List<MatchDTO> nextMatchesOrLiveMatches = matchService.getNextMatchesOrLiveMatches(3);
        model.addAttribute("nextMatchesOrLiveMatches", nextMatchesOrLiveMatches);

        //last-news
        List<New> latestNews = newService.getLatestNews();
        model.addAttribute("latestNews", latestNews);

        //next match
        Match randomNextMatch = matchService.getRandomNextMatch();
        model.addAttribute("randomNextMatch", randomNextMatch);

        return "customer_homepage";
    }



}
