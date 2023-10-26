package com.example.eproject4.Controller.user;
import com.example.eproject4.DTO.Response.PlayerDTO;
import com.example.eproject4.DTO.Response.TeamDTO;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Service.MatchService;
import com.example.eproject4.Service.PlayerService;
import com.example.eproject4.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamUserController {
    private TeamService teamService;
    private PlayerService playerService;
    private MatchService matchService;
    @Autowired
    public TeamUserController(TeamService teamService, PlayerService playerService, MatchService matchService) {
        this.teamService = teamService;
        this.playerService = playerService;
        this.matchService = matchService;
    }

    @RequestMapping("")
    public String teams(Model model) {
        model.addAttribute("overlay_title", "Teams");
        model.addAttribute("title", "Teams");
        model.addAttribute("description", "List of competing teams of the season");

        List<TeamDTO> teams = teamService.getAllTeams();
        model.addAttribute("teams", teams);

        return "customer_teams";
    }
    @RequestMapping("/detail/{id}")
    public String teamDetail(@PathVariable Long id, Model model) {
        // Lấy thông tin của đội bóng
        List<PlayerDTO> playerDTOList = playerService.findAllByTeam_id(id);
        TeamDTO teamDetail = teamService.getTeamById(id);
        String tenDoiBong = teamDetail.getName();
        String tenSVD = teamDetail.getHome_stadium();

        // Lấy 4 trận đấu gần đây đã kết thúc của đội bóng dựa trên home_team_id hoặc away_team_id
        List<Match> recentMatches = matchService.findRecentMatchesByTeamId(id);

        model.addAttribute("title", "Teams Detail");
        model.addAttribute("overlay_title", tenDoiBong);
        model.addAttribute("description", tenSVD);
        model.addAttribute("teamDetail", teamDetail);
        model.addAttribute("players", playerDTOList);
        model.addAttribute("recentMatches", recentMatches);

        return "customer_team_detail";
    }
}
