package com.example.eproject4.Service;

import com.example.eproject4.DTO.Request.StadiumRequest;
import com.example.eproject4.DTO.Response.StadiumDTO;
import com.example.eproject4.Entity.Stadium;
import com.example.eproject4.Repository.StadiumRepository;
import com.example.eproject4.Utils.Helper;
import com.example.eproject4.Utils.ModelToDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public StadiumDTO createStadium(StadiumRequest stadiumRequest) {
        Stadium stadium = new Stadium();

        stadium.setName(stadiumRequest.getName());
        stadium.setDescription(stadiumRequest.getDescription());
        stadium.setStatus(1);

        stadium = stadiumRepository.save(stadium);
        return modelToDtoConverter.convertToDto(stadium, StadiumDTO.class);
    }

    public Stadium updateStadium(StadiumRequest stadiumRequest) {
        try {
            Stadium stadium = stadiumRepository.getById(stadiumRequest.getId());

            stadium.setName(stadiumRequest.getName());
            stadium.setDescription(stadiumRequest.getDescription());
            stadium.setStatus(stadiumRequest.getStatus());

            return stadiumRepository.save(stadium);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteStadium(Long id) {
        stadiumRepository.deleteById(id);
    }

}

