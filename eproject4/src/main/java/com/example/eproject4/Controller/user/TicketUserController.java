package com.example.eproject4.Controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tickets")
public class TicketUserController {
    @RequestMapping("")
    public String tickets(Model model) {
        model.addAttribute("overlay_title", "Tickets");
        model.addAttribute("title", "Tickets");
        model.addAttribute("description", "Day la danh sach ve cac tran dau");

        return "customer_tickets";
    }

    @RequestMapping("/detail")
    public String ticket_detail(Model model) {
        model.addAttribute("overlay_title", "Ticket Detail");
        model.addAttribute("title", "Ticket Detail");
        model.addAttribute("description", "Day la chi tiet ve tran dau");

        return "customer_ticket_detail";
    }
}
