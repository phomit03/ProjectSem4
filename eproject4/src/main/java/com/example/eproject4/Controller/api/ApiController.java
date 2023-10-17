package com.example.eproject4.Controller.api;

import com.example.eproject4.DTO.Response.MatchDetailEventDTO;
import com.example.eproject4.Service.MatchDetailEventService;
import com.example.eproject4.Service.MatchDetailService;
import com.example.eproject4.Service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private MatchDetailEventService matchDetailEventService;
    @Autowired
    private MatchDetailService matchDetailService;
    @Autowired
    private MatchService matchService;

    @PostMapping("/getTeamEvent")
    public ResponseEntity<Object> getTeamEvent(@RequestBody Map<String, Object> requestBody, RedirectAttributes attributes) {
        Long homeTeamId = Long.parseLong(requestBody.get("homeTeamId").toString());
        Long awayTeamId = Long.parseLong(requestBody.get("awayTeamId").toString());
        Long matchId = Long.parseLong(requestBody.get("matchId").toString());


        Long homeTeamScore = matchDetailEventService.countEvent(homeTeamId, matchId, 1);
        Long awayTeamScore = matchDetailEventService.countEvent(awayTeamId, matchId, 1);
        ArrayList response = new ArrayList<>();
        response.add(homeTeamScore);
        response.add(awayTeamScore);
        return ResponseEntity.ok(response);
    }
}
