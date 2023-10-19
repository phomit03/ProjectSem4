package com.example.eproject4.Repository;

import com.example.eproject4.Entity.MatchDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchDetailRepository extends JpaRepository<MatchDetail, Long> {
    @Query("SELECT md FROM MatchDetail md WHERE md.match_id = :matchId")
    MatchDetail findMatchDetailByMatchId (@Param("matchId") Long matchId);
}
