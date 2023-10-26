package com.example.eproject4.Service;

import com.example.eproject4.DTO.Response.NewDTO;
import com.example.eproject4.Entity.New;
import com.example.eproject4.Entity.User;
import com.example.eproject4.Repository.NewRepository;
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
public class NewService {
    @Autowired
    private Helper helper;
    @Autowired
    private final ModelToDtoConverter modelToDtoConverter;
    @Autowired
    private final NewRepository newRepository;

    public NewService(ModelToDtoConverter modelToDtoConverter, NewRepository newRepository) {
        this.modelToDtoConverter = modelToDtoConverter;
        this.newRepository = newRepository;
    }

    public List<NewDTO> getAllNews() {
        List<New> news = newRepository.findAll();

        return news.stream().map(news1 -> modelToDtoConverter.convertToDto(news1, NewDTO.class)).collect(Collectors.toList());
    }

    public New createNew(NewDTO newDTO, MultipartFile logo) throws IOException {
        New aNew = new New();
        aNew.setTitle(newDTO.getTitle());
        aNew.setSub_title(newDTO.getSub_title());
        aNew.setContent(newDTO.getContent());
        aNew.setStatus(1);
        if (!logo.isEmpty()) {
            String imageUrl = helper.uploadImage(logo);
            aNew.setNew_img(imageUrl);
        }
        return newRepository.save(aNew);
    }

    public NewDTO getNewById(Long id) {
        New aNew = newRepository.findById(id).orElse(null);
        return modelToDtoConverter.convertToDto(aNew, NewDTO.class);
    }

    public New update (NewDTO newDTO, MultipartFile logo) {
        try {
            New aNew = newRepository.getById(newDTO.getId());
            if (!logo.isEmpty()) {
                String logo_img = helper.uploadImage(logo);
                aNew.setNew_img(logo_img);
            }
            aNew.setTitle(newDTO.getTitle());
            aNew.setSub_title(newDTO.getSub_title());
            aNew.setContent(newDTO.getContent());
            aNew.setStatus(newDTO.getStatus());
            aNew.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
            return newRepository.save(aNew);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public NewDTO findById(Long id) {
        New aNew = newRepository.getById(id);
        return modelToDtoConverter.convertToDto(aNew, NewDTO.class);
    }

    public void softDelete(Long id) {
        Optional<New> optionalEntity = newRepository.findById(id);
        if (optionalEntity.isPresent()) {
            New aNew = optionalEntity.get();
            aNew.setStatus(0);
            newRepository.save(aNew);
        } else {
            throw new EntityNotFoundException("Entity with id " + id + " not found.");
        }
    }

    //phan trang
    public Page<New> findPaginated(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.newRepository.findAll(pageable);
    }

    public List<New> getLatestNews() {
        return newRepository.findLatestThreeNews();
    }

    public List<New> getTwoLatestNews() {
        return newRepository.findLatestTwoNews();
    }
}
