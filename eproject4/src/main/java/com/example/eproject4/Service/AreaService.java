package com.example.eproject4.Service;

import com.example.eproject4.DTO.Request.AreaRequest;
import com.example.eproject4.DTO.Response.AreaDTO;
import com.example.eproject4.Entity.Area;
import com.example.eproject4.Entity.VideoHighlight;
import com.example.eproject4.Repository.AreaRepository;
import com.example.eproject4.Utils.ModelToDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AreaService {
    @Autowired
    private final AreaRepository areaRepository;
    @Autowired
    private final ModelToDtoConverter modelToDtoConverter;

    @Autowired
    public AreaService(AreaRepository areaRepository, ModelToDtoConverter modelToDtoConverter) {
        this.areaRepository = areaRepository;
        this.modelToDtoConverter = modelToDtoConverter;
    }

    public List<AreaDTO> getAllAreas() {
        List<Area> areas = areaRepository.findAll();

        return areas.stream().map(area -> modelToDtoConverter.convertToDto(area, AreaDTO.class))
                .collect(Collectors.toList());
    }

    public AreaDTO getAreaById(Long id) {
        Area area = areaRepository.findById(id).orElse(null);
        return modelToDtoConverter.convertToDto(area, AreaDTO.class);
    }

    public Area getAreaByIdArea(int id) {
        Area area = areaRepository.findByAreaById(id);
        return area;
    }

    public AreaDTO createArea(AreaDTO areaDTO) {
        Area area = new Area();

        area.setArea_name(areaDTO.getArea_name());
        area.setStadium_id(areaDTO.getStadium_id());
        area.setStatus(1);

        area = areaRepository.save(area);
        return modelToDtoConverter.convertToDto(area, AreaDTO.class);
    }

    public Area updateArea(AreaDTO areaDTO) {
        try {
            Area area = areaRepository.getById(areaDTO.getId());

            area.setArea_name(areaDTO.getArea_name());
            area.setStadium_id(areaDTO.getStadium_id());
            area.setStatus(areaDTO.getStatus());
            area.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));

            return areaRepository.save(area);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void softDelete(Long id) {
        Optional<Area> optionalEntity = areaRepository.findById(id);
        if (optionalEntity.isPresent()) {
            Area area = optionalEntity.get();
            area.setStatus(0);
            areaRepository.save(area);
        } else {
            throw new EntityNotFoundException("Entity with id " + id + " not found.");
        }
    }


    // phan trang
    public Page<Area> findPaginated(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.areaRepository.findAll(pageable);
    }

    public List<AreaDTO> getAreaByStadiumId (Long stadiumId) {
        List<Area> areas = areaRepository.findByStadium(stadiumId);
        return areas.stream().map(area -> modelToDtoConverter.convertToDto(area, AreaDTO.class))
                .collect(Collectors.toList());
    }
}

