package com.example.eproject4.Controller.admin;

import com.example.eproject4.DTO.Request.MatchRequest;
import com.example.eproject4.DTO.Request.VideoHighlightRequest;
import com.example.eproject4.DTO.Response.NewDTO;
import com.example.eproject4.DTO.Response.VideoHighlightDTO;
import com.example.eproject4.Entity.New;
import com.example.eproject4.Entity.VideoHighlight;
import com.example.eproject4.Service.NewService;
import com.example.eproject4.Service.VideoHighlightService;
import org.springframework.data.domain.Page;
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
public class VideoHighlightController {
    private final VideoHighlightService videoHighlightService;

    public VideoHighlightController(VideoHighlightService videoHighlightService) {
        this.videoHighlightService = videoHighlightService;
    }

    @GetMapping("/video_highlights")
    public String getAllVideoHighlights(Model model){
        model.addAttribute("title", "Video Highlights");
        return findPaginated(1, model);
    }

    @GetMapping("/video_highlight/create")
    public String showCreateForm(Model model) {
        model.addAttribute("videoHighlightRequest", new VideoHighlightRequest());
        return "admin_videohighlight_create";
    }

    @PostMapping("/video_highlight/create/save")
    public String createVideoHighlight(@ModelAttribute VideoHighlightRequest videoHighlightRequest,
                                       @RequestParam("image_highlight") MultipartFile image_highlight,
                                       RedirectAttributes attributes) {
        try {
            videoHighlightService.create(videoHighlightRequest, image_highlight);
            attributes.addFlashAttribute("success", "Create Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to create!");

        }
        return "redirect:/admin/video_highlights";
    }

    @GetMapping("/video_highlight/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        VideoHighlightDTO videoHighlights = videoHighlightService.getVideoHighlightById(id);
        if (videoHighlights == null) {
            return "redirect:/admin/video_highlights";
        }
        model.addAttribute("videoHighlights", videoHighlights);
        return "admin_videohighlight_update";
    }

    @PostMapping("/video_highlight/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("videoHighlightRequest") VideoHighlightRequest videoHighlightRequest,
                         @RequestParam(value = "image_highlight", required = false) MultipartFile image_highlight, RedirectAttributes attributes) {
        try {
            videoHighlightService.update(videoHighlightRequest, image_highlight);
            attributes.addFlashAttribute("success", "Update Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }
        return "redirect:/admin/video_highlights";
    }

    @GetMapping("/video_highlight/delete/{id}")
    public ResponseEntity<String> softDelete(@PathVariable Long id) {
        try {
            videoHighlightService.softDelete(id);
            return ResponseEntity.ok("Delete Video Highlight successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
        }
    }

    // phan trang
    @GetMapping("/video_highlights/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                Model model) {
        int pageSize = 6;
        Page<VideoHighlight> page = videoHighlightService.findPaginated(pageNo, pageSize);
        List<VideoHighlight> videoHighlights = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("videoHighlights", videoHighlights);
        return "admin_videohighlights";
    }
}
