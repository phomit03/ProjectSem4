package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.Response.NewDTO;
import com.example.eproject4.Entity.New;
import com.example.eproject4.Entity.User;
import com.example.eproject4.Repository.NewRepository;
import com.example.eproject4.Service.NewService;
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
@RequestMapping("/admin")
public class NewController {
    private final NewService newService;
    private final NewRepository newRepository;

    public NewController(NewService newService,NewRepository newRepository) {
        this.newService = newService;
        this.newRepository = newRepository;
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
    public ResponseEntity<String> softDelete(@PathVariable Long id) {
        try {
            newService.softDelete(id);
            return ResponseEntity.ok("Delete new successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
        }
    }

    //phan trang
    @GetMapping("/new")
    public String getAllNews(Model model,
                             @RequestParam(name = "title", required = false) String title,
                             @RequestParam(name = "status", required = false) Integer status
    ) {
        model.addAttribute("title", "News");
        return findPaginated(1, model, title, status);
    }

    @GetMapping("/new/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                Model model,
                                @RequestParam(name = "title", required = false) String title,
                                @RequestParam(name = "status", required = false) Integer status
    ) {
        int pageSize = 6;

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        List<New> result = newRepository.searchNews(title, status, pageable);
        Page<New> page = new PageImpl<>(result, pageable,newRepository.searchNews1(title, status).size());
        List<New> news = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("news", news);
        model.addAttribute("title", title);
//        model.addAttribute("title", "News");
        model.addAttribute("status", status);
        return "admin_new";
    }
}
