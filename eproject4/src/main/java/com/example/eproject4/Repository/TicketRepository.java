package com.example.eproject4.Repository;

import com.example.eproject4.Entity.Match;
import com.example.eproject4.Entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{
    @Query("SELECT e FROM Ticket e WHERE e.match = :matchIdValue")
    List<Ticket> findByMatchId(@Param("matchIdValue") Optional<Match> matchId);

    @Query("SELECT t FROM Ticket t WHERE t.id = :id")
    List<Ticket> findIdTicket(@Param("id") Integer id);
}