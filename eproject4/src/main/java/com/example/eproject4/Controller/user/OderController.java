package com.example.eproject4.Controller.user;

import com.example.eproject4.Entity.Match;
import com.example.eproject4.Entity.Ticket;
import com.example.eproject4.Entity.User;
import com.example.eproject4.Entity.cart_order.Cart;
import com.example.eproject4.Repository.TicketRepository;
import com.example.eproject4.Repository.cart_order.CartRepository;
import com.example.eproject4.Service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/logged")
public class OderController {
    private final CartRepository cartRepository;
    private final TicketRepository ticketRepository;

    public OderController(CartRepository cartRepository, TicketRepository ticketRepository) {
        this.cartRepository = cartRepository;
        this.ticketRepository = ticketRepository;
    }

    @RequestMapping("/order")
    public String order(Model model, HttpSession session) {
      /*  model.addAttribute("overlay_title", "Tickets");
        model.addAttribute("title", "Tickets");
        model.addAttribute("description", "Day la danh sach ve cac tran dau");

        List<Match> upcomingMatches = ticketService.getUpcomingMatches();

        model.addAttribute("upcomingMatches", upcomingMatches);
//        model.addAttribute("pastMatches", ticketService.getPastMatches());*/

        /*List<Cart> carts = cartRepository.findAll();
        model.addAttribute("carts", carts);*/
        //User loggedInUser = (User) session.getAttribute("loggedInUser");
        /*User loggedInUser = (User) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);*/
        List<Ticket> tickets = ticketRepository.findAll();
        model.addAttribute("tickets", tickets);
        return "shopping_cart";
    }

    @RequestMapping("/payorder")
    public String PayOrder(Model model) {

      /*  model.addAttribute("overlay_title", "Tickets");
        model.addAttribute("title", "Tickets");
        model.addAttribute("description", "Day la danh sach ve cac tran dau");

        List<Match> upcomingMatches = ticketService.getUpcomingMatches();

        model.addAttribute("upcomingMatches", upcomingMatches);
//        model.addAttribute("pastMatches", ticketService.getPastMatches());*/

        List<Cart> carts = cartRepository.findAll();
        model.addAttribute("carts", carts);
        return "shopping_cart";
    }
}
