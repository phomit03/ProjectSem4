package com.example.eproject4.Controller.admin;

import com.example.eproject4.Entity.Feedback;
import com.example.eproject4.Service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/feedbacks")
    public String getAllFeedback(Model model){
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        model.addAttribute("title", "Feedbacks");
        model.addAttribute("feedbacks", feedbacks);
        return "admin_feedback";
    }
}
