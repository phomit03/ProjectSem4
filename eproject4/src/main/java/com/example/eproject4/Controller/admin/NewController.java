package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.Response.NewDTO;
import com.example.eproject4.Entity.New;
import com.example.eproject4.Entity.User;
import com.example.eproject4.Service.NewService;
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
@RequestMapping("/admin")
public class NewController {
    private final NewService newService;

    public NewController(NewService newService) {
        this.newService = newService;
    }

    @GetMapping("/new")
    public String getAllNews(Model model){
        model.addAttribute("title", "News");
        return findPaginated(1, model);

    }
    @GetMapping("/new/create")
    public String showCreateForm(Model model) {
        model.addAttribute("newDTO", new NewDTO());
        return "admin_new_create";
    }


    @PostMapping("/new/create/save")
    public String createNew(@ModelAttribute NewDTO newDTO, @RequestParam("logo") MultipartFile logo, RedirectAttributes attributes) {
        try {
            newService.createNew(newDTO, logo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/new";
    }

    @GetMapping("/new/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        NewDTO news = newService.getNewById(id);
        if (news == null) {
            return "redirect:/admin/new";
        }

        model.addAttribute("newDTO", news);
        return "admin_new_update";
    }

    @PostMapping("/new/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("newDTO") NewDTO newDTO, @RequestParam(value = "logo", required = false) MultipartFile logo, RedirectAttributes attributes) {
        try {
            newService.update(newDTO, logo);
            attributes.addFlashAttribute("success", "Update Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }
        return "redirect:/admin/new";
    }

    @GetMapping("/new/delete/{id}")
    public ResponseEntity<String> deleteNewById(@PathVariable Long id) {
        try {
            newService.delete(id);
            return ResponseEntity.ok("Delete new successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
        }
    }

    // phan trang
    @GetMapping("/new/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                Model model) {
        int pageSize = 6;
        Page<New> page = newService.findPaginated(pageNo, pageSize);
        List<New> news = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("news", news);
        return "admin_new";
    }
}
