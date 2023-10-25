package com.example.eproject4.Service;

import com.example.eproject4.DTO.Request.TicketRequest;
import com.example.eproject4.DTO.Response.AreaDTO;
import com.example.eproject4.DTO.Response.TicketDTO;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Entity.Ticket;
import com.example.eproject4.Repository.MatchRepository;
import com.example.eproject4.Repository.TicketRepository;
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
public class TicketService {
    @Autowired
    private final TicketRepository ticketRepository;

    @Autowired
    private final MatchRepository matchRepository;

    @Autowired
    private final ModelToDtoConverter modelToDtoConverter;

    @Autowired
    public TicketService(TicketRepository ticketRepository, MatchRepository matchRepository, ModelToDtoConverter modelToDtoConverter) {
        this.ticketRepository = ticketRepository;
        this.matchRepository = matchRepository;
        this.modelToDtoConverter = modelToDtoConverter;
    }

    public List<TicketDTO> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();

        return tickets.stream().map(ticket -> modelToDtoConverter.convertToDto(ticket, TicketDTO.class))
                .collect(Collectors.toList());
    }

    public TicketDTO getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        return modelToDtoConverter.convertToDto(ticket, TicketDTO.class);
    }
    public List<TicketDTO> getTicketByMatchId(Long matchId) {
        List<Ticket> tickets = ticketRepository.findByMatch_id(matchId);
        return tickets.stream().map(ticket -> modelToDtoConverter.convertToDto(ticket, TicketDTO.class))
                .collect(Collectors.toList());
    }

    public TicketDTO createTicket(TicketRequest ticketRequest) {
        Ticket ticket = new Ticket();

        ticket.setArea(ticketRequest.getArea_id());
        ticket.setMatch(ticketRequest.getMatch_id());
        ticket.setQuantity(ticketRequest.getQuantity());
        ticket.setPrice(ticketRequest.getPrice());
        ticket.setStatus(1);

        ticket = ticketRepository.save(ticket);
        return modelToDtoConverter.convertToDto(ticket, TicketDTO.class);
    }

    public TicketDTO create(Long matchId, AreaDTO areaDTO) {
        Ticket ticket = new Ticket();

        ticket.setArea_id(areaDTO.getId());
        ticket.setMatch_id(matchId);
        ticket.setQuantity(100);
        ticket.setPrice(100F);
        ticket.setStatus(1);

        ticket = ticketRepository.save(ticket);
        return modelToDtoConverter.convertToDto(ticket, TicketDTO.class);
    }

    public Ticket updateTicket(TicketRequest ticketRequest) {
        try {
            Ticket ticket = ticketRepository.getById(ticketRequest.getId());

            ticket.setArea(ticketRequest.getArea_id());
            ticket.setMatch(ticketRequest.getMatch_id());
            ticket.setQuantity(ticketRequest.getQuantity());
            ticket.setPrice(ticketRequest.getPrice());
            ticket.setStatus(ticketRequest.getStatus());
            ticket.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));

            return ticketRepository.save(ticket);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Ticket update(Long ticketId, Integer quantity, Float price) {
        try {
            Ticket ticket = ticketRepository.getById(ticketId);
            ticket.setPrice(price);
            ticket.setQuantity(quantity);
            return ticketRepository.save(ticket);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void softDelete(Long id) {
        Optional<Ticket> optionalEntity = ticketRepository.findById(id);
        if (optionalEntity.isPresent()) {
            Ticket ticket = optionalEntity.get();
            ticket.setStatus(0);
            ticketRepository.save(ticket);
        } else {
            throw new EntityNotFoundException("Entity with id " + id + " not found.");
        }
    }

    //Retrieve matches that have not yet taken place or took place 15 minutes before
    public List<Match> getUpcomingMatches() {
        LocalDateTime currentTimeMinus15Minutes = LocalDateTime.now().minusMinutes(15);
        return matchRepository.findMatchesAfterTimeThreshold(currentTimeMinus15Minutes);
    }

    //Retrieve matches that have already taken place or take place after 15 minutes
    public List<Match> getPastMatches() {
        LocalDateTime currentTimeMinus15Minutes = LocalDateTime.now().minusMinutes(15);
        return matchRepository.findMatchesBeforeTimeThreshold(currentTimeMinus15Minutes);
    }

    /*public List<TicketDTO> getAllTicketsByIdMath(int matchid) {

        List<Ticket> tickets = ticketRepository.findByMatchId(matchid);

        return tickets.stream().map(ticket -> modelToDtoConverter.convertToDto(ticket, TicketDTO.class))
                .collect(Collectors.toList());
    }*/

    public List<Ticket> getAllTicketsByIdMath(int matchid) {

        long longValue = (long) matchid;
        Optional<Match> match = matchRepository.findById(longValue);
        List<Ticket> tickets = ticketRepository.findByMatchId(match);
        return tickets;

    }

    /*public List<Ticket> getAllTicketsById(int matchid) {

        long longValue = (long) matchid;
        List<Ticket> tickets = ticketRepository.findByMatchId(matchRepository.findById(longValue));
        return  tickets;

    }*/

    //    public List<Match> getPastMatches() {
//        return matchRepository.findPastMatches();
//    }
//phan trang
    public Page<Ticket> findPaginated(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.ticketRepository.findAll(pageable);
    }
}

