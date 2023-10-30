package com.example.eproject4.Repository;

import com.example.eproject4.DTO.Response.PlayerDTO;
import com.example.eproject4.Entity.Player;
import com.example.eproject4.Entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query("SELECT p FROM Player p WHERE p.team_id = :id AND p.status = 1")
    List<Player> findAllByTeam_id (Long id);

    @Query(value = "SELECT p.* FROM player p " +
            "LEFT JOIN team t ON p.team_id = t.id " +
            "WHERE (:name is null or p.name like CONCAT('%', :name, '%')) " +
            "AND (:team is null or t.name like CONCAT('%', :team, '%')) " +
            "AND (:national is null or p.national like CONCAT('%', :national, '%')) " +
            "AND (:position is null or p.position like CONCAT('%', :position, '%'))", nativeQuery = true)
    List<Player> searchPlayers(@Param("name") String name, @Param("team") String team,
                               @Param("national") String national, @Param("position") String position, Pageable pageable);

    @Query(value = "SELECT p.* FROM player p " +
            "LEFT JOIN team t ON p.team_id = t.id " +
            "WHERE (:name is null or p.name like CONCAT('%', :name, '%')) " +
            "AND (:team is null or t.name like CONCAT('%', :team, '%')) " +
            "AND (:national is null or p.national like CONCAT('%', :national, '%')) " +
            "AND (:position is null or p.position like CONCAT('%', :position, '%'))",
            nativeQuery = true)
    List<Player> searchPlayers1(@Param("name") String name, @Param("team") String team,
                               @Param("national") String national, @Param("position") String position);

}
