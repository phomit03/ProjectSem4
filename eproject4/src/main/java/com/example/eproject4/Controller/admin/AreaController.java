package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.Response.AreaDTO;
import com.example.eproject4.DTO.Response.NewDTO;
import com.example.eproject4.DTO.Response.StadiumDTO;
import com.example.eproject4.DTO.Response.TeamDTO;
import com.example.eproject4.Entity.Area;
import com.example.eproject4.Entity.New;
import com.example.eproject4.Service.AreaService;
import com.example.eproject4.Service.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/admin/area")
public class AreaController {
    private AreaService areaService;

    private StadiumService stadiumService;

    @Autowired
    public AreaController(AreaService areaService, StadiumService stadiumService) {
        this.areaService = areaService;
        this.stadiumService = stadiumService;
    }

    @GetMapping
    public String getAllAreas(Model model){
        model.addAttribute("title", "Areas");
        return findPaginated(1, model);
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<StadiumDTO> stadiums = stadiumService.getAllStadiums();
        model.addAttribute("stadiums", stadiums);
        model.addAttribute("areas", new AreaDTO());
        return "admin_area_create";
    }

    @PostMapping("/create/save")
    public String createArea(@ModelAttribute AreaDTO areaDTO) {
        try {
            areaService.createArea(areaDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/area";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        AreaDTO area = areaService.getAreaById(id);
        List<StadiumDTO> stadiums = stadiumService.getAllStadiums();
        model.addAttribute("stadiums", stadiums);
        if (area == null) {
            return "redirect:/admin/area";
        }
        model.addAttribute("areas", area);
        return "admin_area_update";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,Model model,
                         @ModelAttribute("areaDTO") AreaDTO areaDTO,
                         RedirectAttributes attributes) {
        List<StadiumDTO> stadiums = stadiumService.getAllStadiums();
        model.addAttribute("stadiums", stadiums);
        try {
            areaService.updateArea(areaDTO);
            attributes.addFlashAttribute("success", "Update Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }
        return "redirect:/admin/area";
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> softDelete(@PathVariable Long id) {
        try {
            areaService.deleteArea(id);
            return ResponseEntity.ok("Delete area successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
        }
    }

    //phan trang
    @GetMapping("/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                Model model) {
        int pageSize = 8;
        Page<Area> page = areaService.findPaginated(pageNo, pageSize);
        List<Area> areas = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("areas", areas);
        return "admin_area";
    }

}
