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
        model.addAttribute("description",
                "List of tickets for matches currently on selling and matches that have stopped selling");

        //Matches that have not yet taken place or are taking place 15 minutes before
        List<Match> upcomingMatches = ticketService.getUpcomingMatches();
        model.addAttribute("upcomingMatches", upcomingMatches);
        //Matches have taken place or are taking place after 15 minutes
        List<Match> pastMatches = ticketService.getPastMatches();
        model.addAttribute("pastMatches", pastMatches);

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
