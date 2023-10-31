package com.example.eproject4.Controller.admin;

import com.example.eproject4.Entity.Feedback;
import com.example.eproject4.Entity.Stadium;
import com.example.eproject4.Repository.FeedbackRepository;
import com.example.eproject4.Service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class FeedbackController {
    private FeedbackService feedbackService;
    private FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackController(FeedbackService feedbackService,FeedbackRepository feedbackRepository) {
        this.feedbackService = feedbackService;
        this.feedbackRepository = feedbackRepository;
    }

//    @GetMapping("/feedbacks")
//    public String getAllFeedback(Model model){
//        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
//        model.addAttribute("title", "Feedbacks");
//        model.addAttribute("feedbacks", feedbacks);
//        return "admin_feedback";
//    }

    //phan trang
    @GetMapping("/feedbacks")
    public String getAllNews(Model model,
                             @RequestParam(name = "name", required = false) String name,
                             @RequestParam(name = "email", required = false) String email
    ) {
        model.addAttribute("title", "Feedbacks");
        return findPaginated(1, model, name, email);
    }

    @GetMapping("/feedbacks/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                Model model,
                                @RequestParam(name = "name", required = false) String name,
                                @RequestParam(name = "email", required = false) String email
    ) {
        int pageSize = 6;

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        List<Feedback> result = feedbackRepository.searchFeedbacks(name, email, pageable);
        Page<Feedback> page = new PageImpl<>(result, pageable,feedbackRepository.searchFeedbacks1(name, email).size());
        List<Feedback> feedbacks = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        return "admin_feedback";
    }
}
