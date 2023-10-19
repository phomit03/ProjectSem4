package com.example.eproject4.Service;

import com.example.eproject4.DTO.Request.AreaRequest;
import com.example.eproject4.DTO.Request.TicketAreaRequest;
import com.example.eproject4.DTO.Response.AreaDTO;
import com.example.eproject4.DTO.Response.TicketAreaDTO;
import com.example.eproject4.Entity.Area;
import com.example.eproject4.Entity.New;
import com.example.eproject4.Entity.TicketArea;
import com.example.eproject4.Repository.AreaRepository;
import com.example.eproject4.Repository.TicketAreaRepository;
import com.example.eproject4.Utils.Helper;
import com.example.eproject4.Utils.ModelToDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketAreaService {
    @Autowired
    private final TicketAreaRepository ticketAreaRepository;
    @Autowired
    private final ModelToDtoConverter modelToDtoConverter;

    @Autowired
    public TicketAreaService(TicketAreaRepository ticketAreaRepository, ModelToDtoConverter modelToDtoConverter) {
        this.ticketAreaRepository = ticketAreaRepository;
        this.modelToDtoConverter = modelToDtoConverter;
    }

    public List<TicketAreaDTO> getAllTicketAreas() {
        List<TicketArea> ticketAreas = ticketAreaRepository.findAll();

        return ticketAreas.stream().map(ticketArea -> modelToDtoConverter.convertToDto(ticketArea, TicketAreaDTO.class))
                .collect(Collectors.toList());
    }

    public TicketAreaDTO getTicketAreaById(Long id) {
        TicketArea ticketArea = ticketAreaRepository.findById(id).orElse(null);
        return modelToDtoConverter.convertToDto(ticketArea, TicketAreaDTO.class);
    }

    public TicketAreaDTO createTicketArea(TicketAreaRequest ticketAreaRequest) {
        TicketArea ticketArea = new TicketArea();

        ticketArea.setArea(ticketAreaRequest.getArea_id());
        ticketArea.setMatch(ticketAreaRequest.getMatch_id());
        ticketArea.setQuantity(ticketAreaRequest.getQuantity());
        ticketArea.setPrice(ticketAreaRequest.getPrice());
        ticketArea.setStatus(1);

        ticketArea = ticketAreaRepository.save(ticketArea);
        return modelToDtoConverter.convertToDto(ticketArea, TicketAreaDTO.class);
    }

    public TicketArea updateTicketArea(TicketAreaRequest ticketAreaRequest) {
        try {
            TicketArea ticketArea = ticketAreaRepository.getById(ticketAreaRequest.getId());

            ticketArea.setArea(ticketAreaRequest.getArea_id());
            ticketArea.setMatch(ticketAreaRequest.getMatch_id());
            ticketArea.setQuantity(ticketAreaRequest.getQuantity());
            ticketArea.setPrice(ticketAreaRequest.getPrice());
            ticketArea.setStatus(ticketAreaRequest.getStatus());
            ticketArea.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));

            return ticketAreaRepository.save(ticketArea);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteTicketArea(Long id) {
        ticketAreaRepository.deleteById(id);
    }

    //phan trang
    public Page<TicketArea> findPaginated(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.ticketAreaRepository.findAll(pageable);
    }
}

