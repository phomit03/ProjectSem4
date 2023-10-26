package com.example.eproject4.Repository;

import com.example.eproject4.DTO.Response.MatchDTO;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Entity.MatchDetail;
import org.springframework.data.domain.Pageable;
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


    //Ticket List
    @Query("SELECT m FROM Match m WHERE m.match_time >= :timeThreshold AND m.status = 1")
    List<Match> findMatchesAfterTimeThreshold(@Param("timeThreshold") LocalDateTime timeThreshold);
    @Query("SELECT m FROM Match m WHERE m.match_time < :timeThreshold AND m.status = 1")
    List<Match> findMatchesBeforeTimeThreshold(@Param("timeThreshold") LocalDateTime timeThreshold);

    // tim 3 tran vua ket thuc
    @Query("SELECT m FROM Match m JOIN MatchDetail md ON m.id = md.match_id WHERE md.match_end = 1 AND m.status = 1")
    List<Match> findAllFinishedMatches();

    // tim 3 tran vua ket thuc
    @Query("SELECT m FROM Match m JOIN MatchDetail md ON m.id = md.match_id WHERE md.match_end = 1 AND m.status = 1 ORDER BY m.match_time DESC")
    List<Match> findLatestFinishedMatches(Pageable pageable);

    // 4 tran gan nhat da end cua 1 clb
    @Query("SELECT m FROM Match m  WHERE (m.home_team_id.id = :teamId OR m.away_team_id.id = :teamId) AND m.match_time < CURRENT_TIMESTAMP ORDER BY m.match_time DESC")
    List<Match> findRecentMatchesByTeamId(@Param("teamId") Long id, Pageable pageable);

    //Next Match (Random)
    @Query(value = "SELECT m FROM Match m JOIN MatchDetail md ON m.id = md.match_id WHERE m.match_time > CURRENT_TIMESTAMP AND m.status = 1 AND md.match_end = 0 ORDER BY m.match_time ASC")
    List<Match> findNextMatch();

    //Upcoming Matches
    @Query(value = "SELECT m FROM Match m JOIN MatchDetail md ON m.id = md.match_id WHERE m.match_time > CURRENT_TIMESTAMP AND m.status = 1 AND md.match_end = 0")
    List<Match> findUpComingMatches();

    //The Matches WasOver
    @Query(value = "SELECT m FROM Match m JOIN MatchDetail md ON m.id = md.match_id WHERE m.match_time < CURRENT_TIMESTAMP AND m.status = 1 AND md.match_end = 1")
    List<Match> findTheMatchesWasOver();


}
