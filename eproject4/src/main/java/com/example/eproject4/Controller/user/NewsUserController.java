package com.example.eproject4.Controller.user;

import com.example.eproject4.DTO.Response.NewDTO;
import com.example.eproject4.Entity.New;
import com.example.eproject4.Service.NewService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsUserController {
    private NewService newService;

    public NewsUserController(NewService newService) {
        this.newService = newService;
    }

    @GetMapping
    public String getAllNews(Model model){
        model.addAttribute("overlay_title", "News");
        model.addAttribute("title", "News");
        model.addAttribute("description", "Always update the hottest and latest football news");
        return findPaginated(1, model);
    }
    //phan trang
    @GetMapping("/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        model.addAttribute("overlay_title", "News");
        model.addAttribute("title", "News");
        model.addAttribute("description", "Always update the hottest and latest football news");

        int pageSize = 6;

        Page<New> page = newService.findPaginated(pageNo, pageSize);
        List<New> news = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("news", news);
        return "customer_news";
    }

    @GetMapping("/detail/{id}")
    public String newById(@PathVariable Long id, Model model){
        NewDTO news = newService.getNewById(id);
        model.addAttribute("aNew", news);
        model.addAttribute("overlay_title", "News Detail");
        model.addAttribute("title", "News Detail");
        model.addAttribute("description", news.getTitle());

        return "customer_news_detail";
    }
}
