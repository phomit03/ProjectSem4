package com.example.eproject4.Service;

import com.example.eproject4.DTO.Request.StadiumRequest;
import com.example.eproject4.DTO.Response.StadiumDTO;
import com.example.eproject4.Entity.New;
import com.example.eproject4.Entity.Stadium;
import com.example.eproject4.Repository.StadiumRepository;
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
public class StadiumService {
    @Autowired
    private Helper helper;
    private final StadiumRepository stadiumRepository;

    @Autowired
    public StadiumService(StadiumRepository stadiumRepository, ModelToDtoConverter modelToDtoConverter) {
        this.stadiumRepository = stadiumRepository;
        this.modelToDtoConverter = modelToDtoConverter;
    }

    @Autowired
    private final ModelToDtoConverter modelToDtoConverter;

    public List<StadiumDTO> getAllStadiums() {
        List<Stadium> stadiums = stadiumRepository.findAll();

        return stadiums.stream().map(stadium -> modelToDtoConverter.convertToDto(stadium, StadiumDTO.class))
                .collect(Collectors.toList());
    }

    public StadiumDTO getStadiumById(Long id) {
        Stadium stadium = stadiumRepository.findById(id).orElse(null);
        return modelToDtoConverter.convertToDto(stadium, StadiumDTO.class);
    }

    public StadiumDTO createStadium(StadiumRequest stadiumRequest, MultipartFile logo) throws IOException {
        Stadium stadium = new Stadium();

        stadium.setName(stadiumRequest.getName());
        stadium.setDescription(stadiumRequest.getDescription());
        stadium.setStatus(1);
        if (!logo.isEmpty()) {
            String imageUrl = helper.uploadImage(logo);
            stadium.setMap_img(imageUrl);
        }

        stadium = stadiumRepository.save(stadium);
        return modelToDtoConverter.convertToDto(stadium, StadiumDTO.class);
    }

    public Stadium updateStadium(StadiumRequest stadiumRequest, MultipartFile logo) {
        try {
            Stadium stadium = stadiumRepository.getById(stadiumRequest.getId());
            if (!logo.isEmpty()) {
                String logo_img = helper.uploadImage(logo);
                stadium.setMap_img(logo_img);
            }
            stadium.setName(stadiumRequest.getName());
            stadium.setDescription(stadiumRequest.getDescription());
            stadium.setStatus(stadiumRequest.getStatus());
            stadium.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));

            return stadiumRepository.save(stadium);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void softDelete(Long id) {
        Optional<Stadium> optionalEntity = stadiumRepository.findById(id);
        if (optionalEntity.isPresent()) {
            Stadium stadium = optionalEntity.get();
            stadium.setStatus(0);
            stadiumRepository.save(stadium);
        } else {
            throw new EntityNotFoundException("Entity with id " + id + " not found.");
        }
    }


    // phan trang
    public Page<Stadium> findPaginated(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.stadiumRepository.findAll(pageable);
    }
}

