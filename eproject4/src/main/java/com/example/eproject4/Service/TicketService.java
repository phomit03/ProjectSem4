package com.example.eproject4.Service;

import com.example.eproject4.DTO.Request.TicketRequest;
import com.example.eproject4.DTO.Response.TicketDTO;
import com.example.eproject4.Entity.Ticket;
import com.example.eproject4.Repository.TicketRepository;
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
public class TicketService {
    @Autowired
    private final TicketRepository ticketRepository;
    @Autowired
    private final ModelToDtoConverter modelToDtoConverter;

    @Autowired
    public TicketService(TicketRepository ticketRepository, ModelToDtoConverter modelToDtoConverter) {
        this.ticketRepository = ticketRepository;
        this.modelToDtoConverter = modelToDtoConverter;
    }

    public List<TicketDTO> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();

        return tickets.stream().map(ticketArea -> modelToDtoConverter.convertToDto(ticketArea, TicketDTO.class))
                .collect(Collectors.toList());
    }

    public TicketDTO getTicketById(Long id) {
        Ticket tickets = ticketRepository.findById(id).orElse(null);
        return modelToDtoConverter.convertToDto(tickets, TicketDTO.class);
    }

    public TicketDTO createTicket(TicketRequest ticketRequest) {
        Ticket tickets = new Ticket();

        tickets.setArea(ticketRequest.getArea_id());
        tickets.setMatch(ticketRequest.getMatch_id());
        tickets.setQuantity(ticketRequest.getQuantity());
        tickets.setPrice(ticketRequest.getPrice());
        tickets.setStatus(1);

        tickets = ticketRepository.save(tickets);
        return modelToDtoConverter.convertToDto(tickets, TicketDTO.class);
    }

    public Ticket updateTicket(TicketRequest ticketRequest) {
        try {
            Ticket tickets = ticketRepository.getById(ticketRequest.getId());

            tickets.setArea(ticketRequest.getArea_id());
            tickets.setMatch(ticketRequest.getMatch_id());
            tickets.setQuantity(ticketRequest.getQuantity());
            tickets.setPrice(ticketRequest.getPrice());
            tickets.setStatus(ticketRequest.getStatus());
            tickets.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));

            return ticketRepository.save(tickets);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    //phan trang
    public Page<Ticket> findPaginated(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.ticketRepository.findAll(pageable);
    }
}

