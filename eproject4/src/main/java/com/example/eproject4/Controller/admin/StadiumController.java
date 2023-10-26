package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.Request.StadiumRequest;
import com.example.eproject4.DTO.Response.StadiumDTO;
import com.example.eproject4.Entity.Stadium;
import com.example.eproject4.Service.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
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
    public String stadiums(Model model) {
        model.addAttribute("title", "Stadiums");
        return findPaginated(1, model);

    }

    @GetMapping("/stadium/new")
    public String create(Model model) {
        StadiumRequest stadiumRequest = new StadiumRequest();
        model.addAttribute("stadium", stadiumRequest);

        return "admin_stadium_create";
    }

    @PostMapping("/stadium/new/save")
    public String create(@ModelAttribute StadiumRequest stadiumRequest, @RequestParam("logo") MultipartFile logo, RedirectAttributes redirectAttributes) {
        try {
            stadiumService.createStadium(stadiumRequest, logo);
            redirectAttributes.addFlashAttribute("success", "Create successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to create!");
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
    public String update(@PathVariable Long id, @ModelAttribute("stadiumRequest") StadiumRequest stadiumRequest,@RequestParam(value = "logo", required = false) MultipartFile logo, RedirectAttributes attributes)
    {
        try {
            StadiumDTO stadium = stadiumService.getStadiumById(id);
            if (stadium == null) {
                return "redirect:/admin/stadiums";
            }

            stadiumService.updateStadium(stadiumRequest, logo);
            attributes.addFlashAttribute("success", "Update Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }
        return "redirect:/admin/stadiums";
    }

    @GetMapping("/stadium/delete/{id}")
    public ResponseEntity<String> softDelete(@PathVariable Long id) {
        try {
            stadiumService.softDelete(id);
            return ResponseEntity.ok("Delete stadium successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
        }
    }

    //phan trang
    @GetMapping("/stadiums/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                Model model) {
        int pageSize = 6;
        Page<Stadium> page = stadiumService.findPaginated(pageNo, pageSize);
        List<Stadium> stadiums = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("stadiums", stadiums);
        return "admin_stadium";
    }
}
