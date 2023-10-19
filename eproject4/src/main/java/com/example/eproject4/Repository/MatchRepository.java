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

    @Query("SELECT m FROM Match m " +
            "JOIN MatchDetail md ON m.id = md.match_id " +
            "WHERE m.match_time <= :currentTime " +
            "AND (md.match_end = 0 OR md.match_end IS NULL) " +
            "ORDER BY m.match_time DESC")
    List<Match> findLiveMatches(@Param("currentTime") LocalDateTime currentTime);

    @Query("SELECT m FROM Match m " +
            "JOIN MatchDetail md ON m.id = md.match_id " +
            "WHERE md.match_end = 1 " +
            "ORDER BY m.match_time DESC")
    List<Match> findLatestFinishedMatch();

    //List Ticket
    @Query("SELECT m FROM Match m WHERE m.match_time >= :timeThreshold")
    List<Match> findMatchesAfterTimeThreshold(@Param("timeThreshold") LocalDateTime timeThreshold);
}
