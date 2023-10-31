package com.example.eproject4.Service;

import com.example.eproject4.DTO.Request.VideoHighlightRequest;
import com.example.eproject4.DTO.Response.MatchDTO;
import com.example.eproject4.DTO.Response.NewDTO;
import com.example.eproject4.DTO.Response.VideoHighlightDTO;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Entity.New;
import com.example.eproject4.Entity.VideoHighlight;
import com.example.eproject4.Repository.NewRepository;
import com.example.eproject4.Repository.VideoHighlightRepository;
import com.example.eproject4.Utils.Helper;
import com.example.eproject4.Utils.ModelToDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoHighlightService {
    @Autowired
    private Helper helper;
    @Autowired
    private final ModelToDtoConverter modelToDtoConverter;
    @Autowired
    private final VideoHighlightRepository videoHighlightRepository;

    public VideoHighlightService(ModelToDtoConverter modelToDtoConverter, VideoHighlightRepository videoHighlightRepository) {
        this.modelToDtoConverter = modelToDtoConverter;
        this.videoHighlightRepository = videoHighlightRepository;
    }

    public List<NewDTO> getAllVideoHighlights() {
        List<VideoHighlight> videoHighlights = videoHighlightRepository.findAll();

        return videoHighlights.stream().map(videoHighlights1 -> modelToDtoConverter.convertToDto(videoHighlights1, NewDTO.class)).collect(Collectors.toList());
    }

    public VideoHighlight create(VideoHighlightRequest videoHighlightRequest, MultipartFile image_highlight)
            throws IOException {
        VideoHighlight videoHighlight = new VideoHighlight();
        if (!image_highlight.isEmpty()) {
            String imageUrl = helper.uploadImage(image_highlight);
            videoHighlight.setImage(imageUrl);
        }
        videoHighlight.setVideo_link(videoHighlightRequest.getVideo_link());
        videoHighlight.setTitle(videoHighlightRequest.getTitle());
        videoHighlight.setStatus(1);
        return videoHighlightRepository.save(videoHighlight);
    }

    public VideoHighlight update(VideoHighlightRequest videoHighlightRequest, MultipartFile image_highlight) {
        try {
            VideoHighlight videoHighlight = videoHighlightRepository.getById(videoHighlightRequest.getId());
            if (!image_highlight.isEmpty()) {
                String imageUrl = helper.uploadImage(image_highlight);
                videoHighlight.setImage(imageUrl);
            }
            videoHighlight.setVideo_link(videoHighlightRequest.getVideo_link());
            videoHighlight.setTitle(videoHighlightRequest.getTitle());
            videoHighlight.setStatus(videoHighlightRequest.getStatus());
            videoHighlight.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
            return videoHighlightRepository.save(videoHighlight);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public VideoHighlightDTO getVideoHighlightById(Long id) {
        VideoHighlight videoHighlight = videoHighlightRepository.findById(id).orElse(null);
        return modelToDtoConverter.convertToDto(videoHighlight, VideoHighlightDTO.class);
    }

    public void softDelete(Long id) {
        Optional<VideoHighlight> optionalEntity = videoHighlightRepository.findById(id);
        if (optionalEntity.isPresent()) {
            VideoHighlight videoHighlight = optionalEntity.get();
            videoHighlight.setStatus(0);
            videoHighlightRepository.save(videoHighlight);
        } else {
            throw new EntityNotFoundException("Entity with id " + id + " not found.");
        }
    }

    public List<VideoHighlight> getLatestVideoHighlight() {
        return videoHighlightRepository.findVideoHightlightNew();
    }

    //phan trang
    public Page<VideoHighlight> findPaginated(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.videoHighlightRepository.findAll(pageable);
    }

}
