package com.example.eproject4.Controller.user;

import com.example.eproject4.DTO.Response.FeedbackDTO;
import com.example.eproject4.Entity.Feedback;
import com.example.eproject4.Service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class FeedbackUserAndAdminController {

    private FeedbackService feedbackService;
    @Autowired
    public FeedbackUserAndAdminController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/admin/feedback")
    public String getAllFeedback(Model model){
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        model.addAttribute("title", "Feedbacks");
        model.addAttribute("feedbacks", feedbacks);
        return "admin_feedback";
    }

    @GetMapping("/feedback")
    public String CreateFeedback(Model model){
        model.addAttribute("feedbacks", new FeedbackDTO());
        return "customer_feedbacks";
    }

    @PostMapping("/feedback")
    public String CreateFeedback(@ModelAttribute FeedbackDTO feedbackDTO, RedirectAttributes attributes){
        try {
            feedbackService.createFB(feedbackDTO);
            attributes.addFlashAttribute("success", "Feedback submitted successfully !!!");

        } catch (Exception e){
            attributes.addFlashAttribute("error", "Feedback fail !!!");
            e.printStackTrace();
        }
        return "redirect:/feedback";
    }

}
