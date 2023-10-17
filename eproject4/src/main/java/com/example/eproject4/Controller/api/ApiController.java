package com.example.eproject4.Controller.api;

import com.example.eproject4.DTO.Response.MatchDetailEventDTO;
import com.example.eproject4.DTO.Response.PlayerDTO;
import com.example.eproject4.Entity.Player;
import com.example.eproject4.Service.MatchDetailEventService;
import com.example.eproject4.Service.MatchDetailService;
import com.example.eproject4.Service.MatchService;
import com.example.eproject4.Service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private MatchDetailEventService matchDetailEventService;
    @Autowired
    private MatchDetailService matchDetailService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private PlayerService playerService;

    @PostMapping("/getTeamEvent")
    public ResponseEntity<Map<String, Object>> getTeamEvent(@RequestBody Map<String, Object> requestBody, RedirectAttributes attributes) {
        Long homeTeamId = Long.parseLong(requestBody.get("homeTeamId").toString());
        Long awayTeamId = Long.parseLong(requestBody.get("awayTeamId").toString());
        Long matchId = Long.parseLong(requestBody.get("matchId").toString());

        Long homeTeamScore = matchDetailEventService.countEvent(homeTeamId, matchId, 1);
        Long awayTeamScore = matchDetailEventService.countEvent(awayTeamId, matchId, 1);

        List<MatchDetailEventDTO> homeTeamEventGoal = matchDetailEventService.getEventsByTeamIdAndMatchIdAndType(homeTeamId, matchId, 1);
        List<MatchDetailEventDTO> awayTeamEventGoal = matchDetailEventService.getEventsByTeamIdAndMatchIdAndType(awayTeamId, matchId, 1);

        Map<String, Object> response = new HashMap<>();
        response.put("homeTeamScore", homeTeamScore);
        response.put("awayTeamScore", awayTeamScore);
        response.put("homeTeamEventGoal", homeTeamEventGoal);
        response.put("awayTeamEventGoal", awayTeamEventGoal);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/getPlayerById")
    public ResponseEntity<PlayerDTO> getTeamEvent(@RequestBody Map<String, Object> requestBody) {
        Long playerId = Long.parseLong(requestBody.get("id").toString());
        PlayerDTO playerDTO = playerService.getPlayerById(playerId);
        return ResponseEntity.ok(playerDTO);
    }
}
