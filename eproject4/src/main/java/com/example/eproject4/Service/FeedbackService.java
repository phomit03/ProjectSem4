package com.example.eproject4.Service;

import com.example.eproject4.DTO.Response.FeedbackDTO;
import com.example.eproject4.Entity.Feedback;
import com.example.eproject4.Repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    private FeedbackRepository feedbackRepository;
    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public Feedback create(FeedbackDTO feedbackDTO){
        Feedback feedback = new Feedback();
        feedback.setName(feedbackDTO.getName());
        feedback.setEmail(feedbackDTO.getEmail());
        feedback.setSubject(feedbackDTO.getSubject());
        feedback.setContent(feedbackDTO.getContent());
                feedback.setStatus(1);
        return feedbackRepository.save(feedback);
    }

    public void delete(Long id){
        feedbackRepository.deleteById(id);
    }
}
