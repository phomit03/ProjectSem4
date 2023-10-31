package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.Response.AreaDTO;
import com.example.eproject4.DTO.Response.NewDTO;
import com.example.eproject4.DTO.Response.StadiumDTO;
import com.example.eproject4.DTO.Response.TeamDTO;
import com.example.eproject4.Entity.Area;
import com.example.eproject4.Entity.Feedback;
import com.example.eproject4.Entity.New;
import com.example.eproject4.Repository.AreaRepository;
import com.example.eproject4.Service.AreaService;
import com.example.eproject4.Service.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private AreaRepository areaRepository;

    @Autowired
    public AreaController(AreaService areaService, StadiumService stadiumService, AreaRepository areaRepository) {
        this.areaService = areaService;
        this.stadiumService = stadiumService;
        this.areaRepository = areaRepository;
    }

//    @GetMapping
//    public String getAllAreas(Model model){
//        model.addAttribute("title", "Areas");
//        return findPaginated(1, model);
//    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<StadiumDTO> stadiums = stadiumService.getAllStadiums();
        model.addAttribute("stadiums", stadiums);
        model.addAttribute("areas", new AreaDTO());
        return "admin_area_create";
    }

    @PostMapping("/create/save")
    public String createArea(@ModelAttribute AreaDTO areaDTO, RedirectAttributes attributes) {
        try {
            areaService.createArea(areaDTO);
            attributes.addFlashAttribute("success", "Create Area Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed To Create!");
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
            areaService.softDelete(id);
            return ResponseEntity.ok("Delete area successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
        }
    }

    //phan trang
    @GetMapping()
    public String getAllNews(Model model,
                             @RequestParam(name = "area_name", required = false) String area_name,
                             @RequestParam(name = "name_stadium", required = false) String name_stadium,
                             @RequestParam(name = "status", required = false) Integer status
    ) {
        model.addAttribute("title", "Areas");
        return findPaginated(1, model, area_name, name_stadium, status);
    }

    @GetMapping("/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                Model model,
                                @RequestParam(name = "area_name", required = false) String area_name,
                                @RequestParam(name = "name_stadium", required = false) String name_stadium,
                                @RequestParam(name = "status", required = false) Integer status
    ) {
        int pageSize = 6;

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        List<Area> result = areaRepository.searchAreas(area_name, name_stadium, status, pageable);
        Page<Area> page = new PageImpl<>(result, pageable,areaRepository.searchAreas1(area_name, name_stadium, status).size());
        List<Area> areas = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("areas", areas);
        model.addAttribute("area_name", area_name);
        model.addAttribute("name_stadium", name_stadium);
        model.addAttribute("status", status);
        return "admin_area";
    }

}
