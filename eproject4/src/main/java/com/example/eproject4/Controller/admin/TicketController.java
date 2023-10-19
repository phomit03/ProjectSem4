package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.Request.TicketAreaRequest;
import com.example.eproject4.DTO.Response.NewDTO;
import com.example.eproject4.DTO.Response.TicketAreaDTO;
import com.example.eproject4.Entity.New;
import com.example.eproject4.Entity.TicketArea;
import com.example.eproject4.Service.TicketAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/ticket")
public class TicketController {
    private final TicketAreaService ticketAreaService;
    @Autowired
    public TicketController(TicketAreaService ticketAreaService) {
        this.ticketAreaService = ticketAreaService;
    }

    @GetMapping("/")
    public String getAllTicketAreas(Model model){
        model.addAttribute("title", "News");
        return findPaginated(1, model);
    }

    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("ticket_area", new TicketAreaDTO());
        return "admin_ticket";
    }

    @PostMapping("/create/save")
    public String createTicketArea(@ModelAttribute TicketAreaRequest ticketAreaRequest, RedirectAttributes attributes) {
        try {
            ticketAreaService.createTicketArea(ticketAreaRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/ticket";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        TicketAreaDTO ticketAreaDTO = ticketAreaService.getTicketAreaById(id);
        if (ticketAreaDTO == null) {
            return "redirect:/admin/ticket";
        }

        model.addAttribute("ticket_area", ticketAreaDTO);
        return "admin_ticket_update";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("ticketAreaRequest") TicketAreaRequest ticketAreaRequest, RedirectAttributes attributes) {
        try {
            ticketAreaService.updateTicketArea(ticketAreaRequest);
            attributes.addFlashAttribute("success", "Update Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }
        return "redirect:/admin/ticket";
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteTicketAreaById(@PathVariable Long id) {
        try {
            ticketAreaService.deleteTicketArea(id);
            return ResponseEntity.ok("Delete ticket successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
        }
    }

    // phan trang
    @GetMapping("/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                Model model) {
        int pageSize = 20;


        Page<TicketArea> page = ticketAreaService.findPaginated(pageNo, pageSize);
        List<TicketArea> ticket_area = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("ticket_area", ticket_area);
        return "admin_ticket";
    }
}
