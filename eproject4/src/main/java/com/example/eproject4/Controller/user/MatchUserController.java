package com.example.eproject4.Controller.user;

import com.example.eproject4.DTO.Response.MatchDTO;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Entity.New;
import com.example.eproject4.Service.MatchService;
import com.example.eproject4.Service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/matches")
public class MatchUserController {
    private final MatchService matchService;
    private final NewService newService;

    @Autowired
    public MatchUserController(MatchService matchService, NewService newService) {
        this.matchService = matchService;
        this.newService = newService;
    }

    @RequestMapping("")
    public String matches(Model model) {
        List<Match> latestFinishedMatches = matchService.findLatestFinishedMatches();
        List<Match> findAllFinishedMatches = matchService.findAllFinishedMatches();
        List<Match> findNextMatch = matchService.getFindNextMatch();
        List<Match> findUpComingMatches = matchService.getUpComingMatches();
        List<Match> findMatchesOver = matchService.getTheMatchesWasOver();
        List<MatchDTO> matches = matchService.getAllMatches();
        //news
        List<New> findNewLatestNews = newService.getTwoLatestNews();

        // Lấy danh sách các trận đấu chưa diễn ra
        List<Match> upcomingMatches = findNextMatch.stream()
                .filter(match -> {
                    // Chuyển đổi LocalDateTime thành Date
                    Date matchDate = Date.from(match.getMatch_time().atZone(ZoneId.systemDefault()).toInstant());

                    // Lấy thời gian hiện tại dưới dạng Date
                    Date currentDate = new Date();

                    // So sánh Date của trận đấu với thời gian hiện tại
                    return matchDate.after(currentDate);
                })
                .collect(Collectors.toList());

        model.addAttribute("overlay_title", "Matches");
        model.addAttribute("title", "Matches");
        model.addAttribute("description", "Follow the schedule and results of the season's matches");
        model.addAttribute("latestFinishedMatches", latestFinishedMatches);
        model.addAttribute("findAllFinishedMatches", findAllFinishedMatches);
        model.addAttribute("findNextMatch", findNextMatch);
        model.addAttribute("findUpComingMatches", findUpComingMatches);
        model.addAttribute("findMatchesOver", findMatchesOver);
        model.addAttribute("findNewLatestNews", findNewLatestNews);
        model.addAttribute("matches", matches);

        return "customer_matches";
    }

}
