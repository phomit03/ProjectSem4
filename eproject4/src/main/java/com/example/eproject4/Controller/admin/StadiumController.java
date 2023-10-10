package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.Request.StadiumRequest;
import com.example.eproject4.DTO.Response.StadiumDTO;
import com.example.eproject4.Service.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class StadiumController {
    private final StadiumService stadiumService;

    @Autowired
    public StadiumController(StadiumService stadiumService) {
        this.stadiumService = stadiumService;
    }

    @RequestMapping("/stadiums")
    public String stadiums(Model model, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 20;
        List<StadiumDTO> allStadiums = stadiumService.getAllStadiums();

        int totalItems = allStadiums.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        List<StadiumDTO> stadiums = allStadiums.subList((page - 1) * pageSize, Math.min(page * pageSize, totalItems));

        model.addAttribute("stadiums", stadiums);
        model.addAttribute("title", "Stadiums");
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "admin_stadium";
    }

    @GetMapping("/stadium/new")
    public String create(Model model) {
        StadiumRequest stadiumRequest = new StadiumRequest();
        model.addAttribute("stadium", stadiumRequest);

        return "admin_stadium_create";
    }

    @PostMapping("/stadium/new/save")
    public String create(@ModelAttribute StadiumRequest stadiumRequest){
        try {
            stadiumService.createStadium(stadiumRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/stadiums";
    }

    @GetMapping("/stadium/edit/{id}")
    public String edit(@PathVariable Long id, Model model)  {
        StadiumDTO stadium = stadiumService.getStadiumById(id);
        if (stadium == null) {
            return "redirect:/admin/stadiums";
        }

        model.addAttribute("stadiumDTO", stadium);
        return "admin_stadium_edit";
    }

    @PostMapping("/stadium/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("stadiumRequest") StadiumRequest stadiumRequest,
                         RedirectAttributes attributes) {
        try {
            stadiumService.updateStadium(stadiumRequest);
            attributes.addFlashAttribute("success", "Update Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }
        return "redirect:/admin/stadiums";
    }

    @GetMapping("/stadium/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            stadiumService.deleteStadium(id);
            return ResponseEntity.ok("Delete successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
        }
    }
}
