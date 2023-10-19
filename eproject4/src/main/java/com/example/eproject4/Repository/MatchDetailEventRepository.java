package com.example.eproject4.Repository;

import com.example.eproject4.Entity.MatchDetailEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchDetailEventRepository extends JpaRepository<MatchDetailEvent, Long> {
    @Query("SELECT e FROM MatchDetailEvent e WHERE e.player_id = :playerId AND e.match_id = :matchId")
    List<MatchDetailEvent> findByPlayerIdAndMatchId(@Param("playerId") Long playerId, @Param("matchId") Long matchId);
    @Query("SELECT e FROM MatchDetailEvent e WHERE e.team_id = :teamId AND e.match_id = :matchId")
    List<MatchDetailEvent> findByMatch_idAndTeam_id(@Param("teamId") Long teamId, @Param("matchId") Long matchId);
    @Query("SELECT e FROM MatchDetailEvent e WHERE e.team_id = :teamId AND e.match_id = :matchId AND e.type = :type")
    List<MatchDetailEvent> findByMatchIdAndTeamIdAndType(@Param("teamId") Long teamId, @Param("matchId") Long matchId, @Param("type") Integer type);

    @Query("SELECT COUNT(e) FROM MatchDetailEvent e WHERE e.team_id = :teamId AND e.match_id = :matchId AND e.type = :eventType")
    Long countEventsByTeamIdAndMatchIdAndType(@Param("teamId") Long teamId, @Param("matchId") Long matchId, @Param("eventType") Integer eventType);

    @Query("SELECT e FROM MatchDetailEvent e WHERE e.id = :id")
    MatchDetailEvent findByIdNew(@Param("id") Long id);
}
