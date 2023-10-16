package com.example.eproject4.Repository;

import com.example.eproject4.DTO.Response.PlayerDTO;
import com.example.eproject4.Entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query("SELECT p FROM Player p WHERE p.team_id = :id")
    List<Player> findAllByTeam_id (Long id);
}
