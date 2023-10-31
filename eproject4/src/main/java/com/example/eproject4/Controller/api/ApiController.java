package com.example.eproject4.Controller.api;

import com.example.eproject4.DTO.Response.MatchDetailDTO;
import com.example.eproject4.DTO.Response.MatchDetailEventDTO;
import com.example.eproject4.DTO.Response.OrderDTO;
import com.example.eproject4.DTO.Response.PlayerDTO;
import com.example.eproject4.Entity.Player;
import com.example.eproject4.Entity.cart_order.Order;
import com.example.eproject4.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    private OrderService orderService;

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
        response.put("matchId", matchId);
        response.put("homeTeamScore", homeTeamScore);
        response.put("awayTeamScore", awayTeamScore);
        response.put("homeTeamEventGoal", homeTeamEventGoal);
        response.put("awayTeamEventGoal", awayTeamEventGoal);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/match/detail")
    public ResponseEntity<Map<String, Object>> getMatchDetail(@RequestBody Map<String, Object> requestBody, RedirectAttributes attributes) {
        Long homeTeamId = Long.parseLong(requestBody.get("homeTeamId").toString());
        Long awayTeamId = Long.parseLong(requestBody.get("awayTeamId").toString());
        Long matchId = Long.parseLong(requestBody.get("matchId").toString());

        Long homeTeamScore = matchDetailEventService.countEvent(homeTeamId, matchId, 1);
        Long awayTeamScore = matchDetailEventService.countEvent(awayTeamId, matchId, 1);

        List<MatchDetailEventDTO> homeTeamEventGoal = matchDetailEventService.getEventsByTeamIdAndMatchIdAndType(homeTeamId, matchId, 1);
        List<MatchDetailEventDTO> awayTeamEventGoal = matchDetailEventService.getEventsByTeamIdAndMatchIdAndType(awayTeamId, matchId, 1);
        MatchDetailDTO matchDetailDTO = matchDetailService.getMatchDetailByMatchId(matchId);

        Map<String, Object> response = new HashMap<>();
        response.put("matchId", matchId);
        response.put("homeTeamScore", homeTeamScore);
        response.put("awayTeamScore", awayTeamScore);
        response.put("homeTeamEventGoal", homeTeamEventGoal);
        response.put("awayTeamEventGoal", awayTeamEventGoal);
        response.put("matchDetailDTO", matchDetailDTO);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/getPlayerById")
    public ResponseEntity<PlayerDTO> getTeamEvent(@RequestBody Map<String, Object> requestBody) {
        Long playerId = Long.parseLong(requestBody.get("id").toString());
        PlayerDTO playerDTO = playerService.getPlayerById(playerId);
        return ResponseEntity.ok(playerDTO);
    }

    @GetMapping("/orders/last7days")
    public ResponseEntity<Map<String, Object>> getOrdersForLast7Days() {
        List<OrderDTO> orderDTOS = orderService.getOrdersForLast7Days();
        Map<String, Object> response = new HashMap<>();
        response.put("orderDTOS", orderDTOS);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders/count-last7days-by-day")
    public ResponseEntity<List<Object[]>> getCountOfOrdersInLast7DaysByDay() {
        List<Object[]> orderCounts = orderService.countOrdersInLast7DaysByDay();
        return ResponseEntity.ok(orderCounts);
    }

    @GetMapping("/orders/total-amount-last7days-by-day")
    public ResponseEntity<List<Object[]>> getTotalAmountByDayInLast7Days() {
        List<Object[]> totalAmountsByDay = orderService.getTotalAmountByDayInLast7Days();
        return ResponseEntity.ok(totalAmountsByDay);
    }
}
