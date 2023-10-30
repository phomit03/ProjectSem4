package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.Request.TicketRequest;

import com.example.eproject4.DTO.Response.*;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Entity.Ticket;
import com.example.eproject4.Repository.MatchRepository;
import com.example.eproject4.Repository.TicketRepository;
import com.example.eproject4.Service.MatchService;
import com.example.eproject4.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class TicketController {
    private final TicketService ticketService;
    private final MatchService matchService;
    private final MatchRepository matchRepository;
    @Autowired
    public TicketController(TicketService ticketService, MatchService matchService,MatchRepository matchRepository) {
        this.ticketService = ticketService;
        this.matchService = matchService;
        this.matchRepository = matchRepository;
    }

    @GetMapping("/ticket/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        List<TicketDTO> ticketDTOS = ticketService.getTicketByMatchId(id);
        model.addAttribute("ticketDTOS", ticketDTOS);
        MatchDTO matchDTO = matchService.getMatchById(id);
        model.addAttribute("matchDTO", matchDTO);
        return "admin_ticket_update";
    }

    @PostMapping("ticket/api/update")
    public ResponseEntity<Map<String, Object>> apiUpdate(@RequestBody Map<String, Object> requestBody, RedirectAttributes attributes) {
        Long ticketId = Long.parseLong(requestBody.get("ticketId").toString());
        Integer quantity = Integer.parseInt(requestBody.get("quantity").toString());
        Float price = Float.parseFloat(requestBody.get("price").toString());

        Ticket ticketUpdated = ticketService.update(ticketId, quantity, price);

        Map<String, Object> response = new HashMap<>();
        response.put("ticketUpdated", ticketUpdated);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("ticketRequest") TicketRequest ticketRequest, RedirectAttributes attributes) {
        try {
            ticketService.updateTicket(ticketRequest);
            attributes.addFlashAttribute("success", "Update Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }
        return "redirect:/admin/ticket";
    }

    @GetMapping("/tickets")
    public String getAllMatches(Model model,
                                @RequestParam(name = "match_time", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate matchTime,
                                @RequestParam(name = "home_team_id",required = false) String homeTeam,
                                @RequestParam(name = "away_team_id",required = false) String awayTeam,
                                @RequestParam(name = "status",required = false) Integer status
    ) {
        model.addAttribute("title", "Tickets");
        return findPaginated1(1, model, matchTime,homeTeam,awayTeam, status);
    }
    @GetMapping("/tickets/{pageNo}")
    public String findPaginated1(@PathVariable(value = "pageNo") int pageNo,
                                Model model,
                                 @RequestParam(name = "match_time", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate matchTime,
                                @RequestParam(name = "home_team_id",required = false) String homeTeam,
                                @RequestParam(name = "away_team_id",required = false) String awayTeam,
                                @RequestParam(name = "status",required = false) Integer status
    ) {
        int pageSize = 6;
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        List<Match> result = matchRepository.searchMatches(matchTime, homeTeam, awayTeam, status, pageable);
        Page<Match> page = new PageImpl<>(result, pageable,matchRepository.searchMatches1(matchTime, homeTeam, awayTeam, status).size());
        List<Match> matches = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("matches", matches);
        model.addAttribute("match_time", matchTime);
        model.addAttribute("home_team_id", homeTeam);
        model.addAttribute("away_team_id", awayTeam);
        model.addAttribute("status", status);
        return "admin_ticket";
    }


}
