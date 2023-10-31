package com.example.eproject4.Controller.user;

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
        model.addAttribute("overlay_title", "Matches");
        model.addAttribute("title", "Matches");
        model.addAttribute("description", "Follow the schedule and results of the season's matches");
        return findPaginated(1, model);
    }

    @GetMapping("/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        List<Match> latestFinishedMatches = matchService.findLatestFinishedMatches();
        List<Match> findAllFinishedMatches = matchService.findAllFinishedMatches();
        List<Match> findNextMatch = matchService.getFindNextMatch();
        List<Match> findMatchesOver = matchService.getTheMatchesWasOver();
        //news
        List<New> findNewLatestNews = newService.getTwoLatestNews();

        model.addAttribute("overlay_title", "Matches");
        model.addAttribute("title", "Matches");
        model.addAttribute("description", "Follow the schedule and results of the season's matches");

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

        int pageSize = 6; // Số lượng trận đấu trên mỗi trang
        Page<Match> page = matchService.findPaginated1(pageNo, pageSize, upcomingMatches);
        List<Match> matches = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("latestFinishedMatches", latestFinishedMatches);
        model.addAttribute("findAllFinishedMatches", findAllFinishedMatches);
        model.addAttribute("findNextMatch", findNextMatch);
        model.addAttribute("findMatchesOver", findMatchesOver);
        model.addAttribute("findNewLatestNews", findNewLatestNews);
        model.addAttribute("matches", matches);

        return "customer_matches";
    }
}