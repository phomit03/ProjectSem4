package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.Request.MatchRequest;
import com.example.eproject4.DTO.Response.*;
import com.example.eproject4.Entity.Area;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Entity.MatchDetail;
import com.example.eproject4.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class MatchController {
    @Autowired
    private final MatchService matchService;
    @Autowired
    private final TeamService teamService;
    @Autowired
    private final StadiumService stadiumService;
    @Autowired
    private final MatchDetailService matchDetailService;
    @Autowired
    private final PlayerService playerService;
    @Autowired
    private final MatchDetailEventService matchDetailEventService;

    @Autowired
    private final SimpMessagingTemplate messagingTemplate;


    @Autowired
    public MatchController(MatchService matchService, TeamService teamService, StadiumService stadiumService, MatchDetailService matchDetailService, PlayerService playerService, MatchDetailEventService matchDetailEventService, SimpMessagingTemplate messagingTemplate) {
        this.matchService = matchService;
        this.teamService = teamService;
        this.stadiumService = stadiumService;
        this.matchDetailService = matchDetailService;
        this.playerService = playerService;
        this.matchDetailEventService = matchDetailEventService;
        this.messagingTemplate = messagingTemplate;
    }

    @RequestMapping("/matches")
    public String matches(Model model) {
        model.addAttribute("title", "Matches");
        return findPaginated(1, model);
    }

    @GetMapping("/match/new")
    public String create(Model model) {
        MatchRequest matchRequest = new MatchRequest();
        model.addAttribute("match", matchRequest);

        List<TeamDTO> teams = teamService.getAllTeams();
        model.addAttribute("teams", teams);

        List<StadiumDTO> stadiums = stadiumService.getAllStadiums();
        model.addAttribute("stadiums", stadiums);

        return "admin_match_create";
    }

    @PostMapping("/match/new/save")
    public String create(@ModelAttribute MatchRequest matchRequest, RedirectAttributes redirectAttributes) {
        try {
            if (Objects.equals(matchRequest.getHome_team_id().getId(), matchRequest.getAway_team_id().getId())) {
                redirectAttributes.addFlashAttribute("error", "You cannot choose 2 same teams! ");
                return "redirect:/admin/match/new";
            }

            if (matchService.checkMatchExist(matchRequest)) {
                matchService.createMatch(matchRequest);
                redirectAttributes.addFlashAttribute("success", "Create successfully!");
            } else  {
                redirectAttributes.addFlashAttribute("error", "Creating a match failed because the time and field were the same or the time and team were the same! ");
                return "redirect:/admin/match/new";
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to create!");
        }
        return "redirect:/admin/matches";
    }

    @GetMapping("/match/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        MatchDTO match = matchService.getMatchById(id);
        if (match == null) {
            return "redirect:/admin/matches";
        }
        List<TeamDTO> teams = teamService.getAllTeams();
        model.addAttribute("teams", teams);
        List<StadiumDTO> stadiums = stadiumService.getAllStadiums();
        model.addAttribute("stadiums", stadiums);
        model.addAttribute("matchDTO", match);
        MatchDetailDTO matchDetailDTO = matchDetailService.getMatchDetailByMatchId(id);
        model.addAttribute("matchDetailDTO", matchDetailDTO);
        List<PlayerDTO> homePlayers = playerService.findAllByTeam_id(match.getHome_team_id().getId());
        List<PlayerDTO> awayPlayers = playerService.findAllByTeam_id(match.getAway_team_id().getId());
        model.addAttribute("homePlayers", homePlayers);
        model.addAttribute("awayPlayers", awayPlayers);
        Long homeTeamScore = matchDetailEventService.countEvent(match.getHome_team_id().getId(), match.getId(), 1);
        Long awayTeamScore = matchDetailEventService.countEvent(match.getAway_team_id().getId(), match.getId(), 1);
        model.addAttribute("homeTeamScore", homeTeamScore);
        model.addAttribute("awayTeamScore", awayTeamScore);
        List<List<MatchDetailEventDTO>> homePlayerEvents = new ArrayList<>();
        for (PlayerDTO homePlayer : homePlayers) {
            List<MatchDetailEventDTO> playerEvents = matchDetailEventService.getEventsByPlayerIdAndMatchId(homePlayer.getId(), id);
            homePlayerEvents.add(playerEvents);
        }
        model.addAttribute("homePlayerEvents", homePlayerEvents);
        List<List<MatchDetailEventDTO>> awayPlayerEvents = new ArrayList<>();
        for (PlayerDTO awayPlayer : awayPlayers) {
            List<MatchDetailEventDTO> playerEvents = matchDetailEventService.getEventsByPlayerIdAndMatchId(awayPlayer.getId(), id);
            awayPlayerEvents.add(playerEvents);
        }
        model.addAttribute("awayPlayerEvents", awayPlayerEvents);
        model.addAttribute("teamEvent", new MatchDetailEventDTO());
        return "admin_match_edit";
    }

    @PostMapping("/match/event/save")
    public String eventSave(@ModelAttribute("teamEvent") MatchDetailEventDTO matchDetailEventDTO, RedirectAttributes redirectAttributes) {
        try {
            MatchDetailEventDTO matchDetailEventDTO1 = matchDetailEventService.create(matchDetailEventDTO);

            MatchDTO matchDTO = matchService.getMatchById(matchDetailEventDTO.getMatch_id());
            Long homeTeamScore = matchDetailEventService.countEvent(matchDTO.getHome_team_id().getId(), matchDTO.getId(), 1);
            Long awayTeamScore = matchDetailEventService.countEvent(matchDTO.getAway_team_id().getId(), matchDTO.getId(), 1);
            List<MatchDetailEventDTO> homeTeamEventGoal = matchDetailEventService.getEventsByTeamIdAndMatchIdAndType(matchDTO.getHome_team_id().getId(), matchDTO.getId(), 1);
            List<MatchDetailEventDTO> awayTeamEventGoal = matchDetailEventService.getEventsByTeamIdAndMatchIdAndType(matchDTO.getAway_team_id().getId(), matchDTO.getId(), 1);
            ScoreUpdate scoreUpdate = new ScoreUpdate();
            scoreUpdate.setMatch_id(matchDTO.getId());
            scoreUpdate.setHomeTeamScore(homeTeamScore);
            scoreUpdate.setAwayTeamScore(awayTeamScore);
            scoreUpdate.setHomeTeamEventGoal(homeTeamEventGoal);
            scoreUpdate.setAwayTeamEventGoal(awayTeamEventGoal);

            messagingTemplate.convertAndSend("/topic/score", scoreUpdate);
            return "redirect:/admin/match/edit/" + matchDetailEventDTO.getMatch_id();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/match/edit/" + matchDetailEventDTO.getMatch_id();
        }
    }

    @PostMapping("/match/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("matchRequest") MatchRequest matchRequest,
                         RedirectAttributes attributes) {
        try {
            if (Objects.equals(matchRequest.getHome_team_id().getId(), matchRequest.getAway_team_id().getId())) {
                attributes.addFlashAttribute("error", "You cannot choose 2 same teams! ");
                return "redirect:/admin/match/edit/" + id;
            }

            if (matchService.checkMatchExist(matchRequest)) {
                matchService.updateMatch(matchRequest);
                attributes.addFlashAttribute("success", "Update Successfully!");
            } else  {
                attributes.addFlashAttribute("error", "Updating a match failed because the time and field were the same or the time and team were the same! ");
                return "redirect:/admin/match/edit/" + id;
            }

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

    @PostMapping("/match/event/delete/{id}")
    public ResponseEntity<String> deleteMatchDetailEvent(@PathVariable Long id) {
        try {
            MatchDetailEventDTO matchDetailEventDTO = matchDetailEventService.findById(id);
            matchDetailEventService.delete(id);
            MatchDTO matchDTO = matchService.getMatchById(matchDetailEventDTO.getMatch_id());
            Long homeTeamScore = matchDetailEventService.countEvent(matchDTO.getHome_team_id().getId(), matchDTO.getId(), 1);
            Long awayTeamScore = matchDetailEventService.countEvent(matchDTO.getAway_team_id().getId(), matchDTO.getId(), 1);
            List<MatchDetailEventDTO> homeTeamEventGoal = matchDetailEventService.getEventsByTeamIdAndMatchIdAndType(matchDTO.getHome_team_id().getId(), matchDTO.getId(), 1);
            List<MatchDetailEventDTO> awayTeamEventGoal = matchDetailEventService.getEventsByTeamIdAndMatchIdAndType(matchDTO.getAway_team_id().getId(), matchDTO.getId(), 1);
            ScoreUpdate scoreUpdate = new ScoreUpdate();
            scoreUpdate.setMatch_id(matchDTO.getId());
            scoreUpdate.setHomeTeamScore(homeTeamScore);
            scoreUpdate.setAwayTeamScore(awayTeamScore);
            scoreUpdate.setHomeTeamEventGoal(homeTeamEventGoal);
            scoreUpdate.setAwayTeamEventGoal(awayTeamEventGoal);
            messagingTemplate.convertAndSend("/topic/score", scoreUpdate);
            return ResponseEntity.ok("Delete event successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
        }
    }

    @PostMapping("/match/detail/update/{id}")
    public String updateMatchDetail(@PathVariable Long id, @ModelAttribute("matchDetailDTO") MatchDetailDTO matchDetailDTO, RedirectAttributes attributes) {
        try {
            System.out.println(matchDetailDTO);
            MatchDetail matchDetail = matchDetailService.updateMatchDetail(matchDetailDTO);
            attributes.addFlashAttribute("success", "Update Success");
            return "redirect:/admin/match/edit/" + matchDetailDTO.getMatch_id();
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Update failed");
            return "redirect:/admin/match/edit/" + matchDetailDTO.getMatch_id();
        }
    }

    //phan trang
    @GetMapping("/matches/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                Model model) {
        int pageSize = 6;
        Page<Match> page = matchService.findPaginated(pageNo, pageSize);
        List<Match> match = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("matches", match);
        return "admin_match";
    }


}
