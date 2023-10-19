package com.example.eproject4.Controller.user;

import com.example.eproject4.Entity.Match;
import com.example.eproject4.Service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class TicketUserController {
    private final TicketService ticketService;
    public TicketUserController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @RequestMapping("/list_tickets")
    public String tickets(Model model) {
        model.addAttribute("overlay_title", "Tickets");
        model.addAttribute("title", "Tickets");
        model.addAttribute("description", "Day la danh sach ve cac tran dau");

        List<Match> upcomingMatches = ticketService.getUpcomingMatches();

        model.addAttribute("upcomingMatches", upcomingMatches);
//        model.addAttribute("pastMatches", ticketService.getPastMatches());

        return "customer_tickets";
    }

    @RequestMapping("/ticket/detail")
    public String ticketDetail(Model model) {
        model.addAttribute("overlay_title", "Ticket Detail");
        model.addAttribute("title", "Ticket Detail");
        model.addAttribute("description", "Day la chi tiet ve tran dau");

        return "customer_ticket_detail";
    }

}
