package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.Request.TicketRequest;

import com.example.eproject4.DTO.Response.*;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Entity.Ticket;
import com.example.eproject4.Service.MatchService;
import com.example.eproject4.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TicketController {
    private final TicketService ticketService;
    private final MatchService matchService;
    @Autowired
    public TicketController(TicketService ticketService, MatchService matchService) {
        this.ticketService = ticketService;
        this.matchService = matchService;
    }

    @RequestMapping("/tickets")
    public String tickets(Model model) {
        model.addAttribute("title", "Tickets");
        return findPaginated(1, model);
    }
    @GetMapping("/tickets/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                Model model) {
        int pageSize = 6;
        Page<Match> page = matchService.findPaginated(pageNo, pageSize);
        List<Match> match = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("matches", match);
        return "admin_ticket";
    }

    @GetMapping("/ticket/edit")
    public String edit() {
        return "admin_ticket_update";
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


}
