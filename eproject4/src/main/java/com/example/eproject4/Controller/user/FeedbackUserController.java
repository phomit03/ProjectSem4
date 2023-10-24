package com.example.eproject4.Controller.user;

import com.example.eproject4.DTO.Response.FeedbackDTO;
import com.example.eproject4.Service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FeedbackUserController {
    @Autowired
    private FeedbackService feedbackService;
    public FeedbackUserController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/feedback")
    public String CreateFeedback(Model model){
        model.addAttribute("feedbacks", new FeedbackDTO());
        model.addAttribute("overlay_title", "Feedback");
        model.addAttribute("title", "Feedback");
        model.addAttribute("description", "Please give us your contributions");

        return "customer_feedbacks";
    }

    @PostMapping("/feedback")
    public String CreateFeedback(@ModelAttribute FeedbackDTO feedbackDTO, RedirectAttributes attributes){
        try {
            feedbackService.create(feedbackDTO);
            attributes.addFlashAttribute("success", "Feedback submitted successfully!");

        } catch (Exception e){
            attributes.addFlashAttribute("error", "Feedback failed !!!");
            e.printStackTrace();
        }
        return "redirect:/feedback";
    }

}
