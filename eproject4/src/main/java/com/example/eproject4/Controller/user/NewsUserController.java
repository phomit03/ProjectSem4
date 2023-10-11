package com.example.eproject4.Controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news")
public class NewsUserController {
    @RequestMapping("")
    public String news(Model model) {
        model.addAttribute("overlay_title", "News");
        model.addAttribute("title", "News");
        model.addAttribute("description", "Always update hot football news");

        return "customer_news";
    }
    @RequestMapping("/detail")
    public String news_detail(Model model) {
        model.addAttribute("title", "News Detail");
        return "customer_news_detail";
    }
}
