package com.example.eproject4.Repository;

import com.example.eproject4.Entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query("SELECT m FROM Match m WHERE m.match_time = :matchTime AND m.stadium_id.id = :stadiumId")
    List<Match> findMatchesByMatchTimeAndStadiumId(@Param("matchTime") LocalDateTime matchTime, @Param("stadiumId") Long stadiumId);

    @Query("SELECT m FROM Match m WHERE m.match_time = :matchTime AND m.home_team_id.id = :homeTeamId")
    List<Match> findMatchesByMatchTimeAndHomeTeamId (@Param("matchTime") LocalDateTime matchTime, @Param("homeTeamId") Long homeTeamId);

    @Query("SELECT m FROM Match m WHERE m.match_time = :matchTime AND m.away_team_id.id = :awayTeamId")
    List<Match> findMatchesByMatchTimeAndAwayTeamId (@Param("matchTime") LocalDateTime matchTime, @Param("awayTeamId") Long awayTeamId);

    @Query("SELECT m, md, mde " +
            "FROM Match m " +
            "LEFT JOIN MatchDetail md ON m.id = md.match_id " +
            "LEFT JOIN MatchDetailEvent mde ON m.id = mde.match_id " +
            "WHERE (m.match_time > :currentDateTime AND m.status = 1) " +
            "OR (m.match_time > :currentDateTime) " +
            "ORDER BY m.match_time ASC")
    List<Match> findNextLiveOrUpcomingMatchesWithDetails(@Param("currentDateTime") LocalDateTime currentDateTime);

    //List Ticket
    @Query("SELECT m FROM Match m WHERE m.match_time >= :timeThreshold")
    List<Match> findMatchesAfterTimeThreshold(@Param("timeThreshold") LocalDateTime timeThreshold);
}
