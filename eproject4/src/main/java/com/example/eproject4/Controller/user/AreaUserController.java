package com.example.eproject4.Controller.user;

import com.example.eproject4.DTO.Response.AreaDTO;
import com.example.eproject4.Entity.Area;
import com.example.eproject4.Service.AreaService;
import com.example.eproject4.Service.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/areas")
public class AreaUserController {
    private AreaService areaService;

    private StadiumService stadiumService;

    @Autowired
    public AreaUserController(AreaService areaService, StadiumService stadiumService) {
        this.areaService = areaService;
        this.stadiumService = stadiumService;
    }

    @GetMapping
    public String getAllAreas(Model model){
        model.addAttribute("overlay_title", "Areas");
        model.addAttribute("title", "Areas");
        model.addAttribute("description", "All Areas");
        return findPaginated(1, model);

    }

    //phan trang
    @GetMapping("/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                Model model) {
        int pageSize = 20;


        Page<Area> page = areaService.findPaginated(pageNo, pageSize);
        List<Area> areas = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("areas", areas);
        return "customer_news";
    }

    @GetMapping("/detail")
    public String news_detail(Model model) {
        model.addAttribute("overlay_title", "Areas");
        model.addAttribute("title", "Areas Detail");
        model.addAttribute("description", "Information Area");

        return "customer_area_detail";
    }

    @GetMapping("/detail/{id}")
    public String newById(@PathVariable Long id, Model model){
        AreaDTO areas = areaService.getAreaById(id);
        model.addAttribute("areas", areas);
        model.addAttribute("overlay_title", "Areas");
        model.addAttribute("title", "Areas Detail");
        model.addAttribute("description", "Information Area");

        return "customer_area_detail";
    }
}
