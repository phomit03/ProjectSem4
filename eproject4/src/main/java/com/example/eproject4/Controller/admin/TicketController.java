package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.Request.TicketRequest;

import com.example.eproject4.DTO.Response.TicketDTO;
import com.example.eproject4.Entity.Ticket;
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
import java.util.List;

@Controller
@RequestMapping("/admin/ticket")
public class TicketController {
    private final TicketService ticketService;
    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/")
    public String getAllTickets(Model model){
        model.addAttribute("title", "News");
        return findPaginated(1, model);
    }

    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("ticket", new TicketDTO());
        return "admin_ticket";
    }

    @PostMapping("/create/save")
    public String createTicket(@ModelAttribute TicketRequest ticketRequest, RedirectAttributes attributes) {
        try {
            ticketService.createTicket(ticketRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/ticket";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        TicketDTO ticketDTO = ticketService.getTicketById(id);
        if (ticketDTO == null) {
            return "redirect:/admin/ticket";
        }

        model.addAttribute("ticket", ticketDTO);
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

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> softDelete(@PathVariable Long id) {
        try {
            ticketService.softDelete(id);
            return ResponseEntity.ok("Delete ticket successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
        }
    }

    // phan trang
    @GetMapping("/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                Model model) {
        int pageSize = 20;
        Page<Ticket> page = ticketService.findPaginated(pageNo, pageSize);
        List<Ticket> ticket = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("ticket", ticket);
        return "admin_ticket";
    }
}
