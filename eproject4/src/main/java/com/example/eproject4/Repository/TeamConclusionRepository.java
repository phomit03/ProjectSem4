package com.example.eproject4.Repository;

import com.example.eproject4.Entity.MatchDetail;
import com.example.eproject4.Entity.TeamConclusion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamConclusionRepository extends JpaRepository<TeamConclusion, Long> {
    @Query("SELECT tc FROM TeamConclusion tc WHERE tc.team_id = :team_id")
    TeamConclusion findByTeam_id (@Param("team_id") Long team_id);

    @Query("SELECT tc FROM TeamConclusion tc ORDER BY tc.point DESC")
    List<TeamConclusion> findAllOrderByPointDesc();
}
