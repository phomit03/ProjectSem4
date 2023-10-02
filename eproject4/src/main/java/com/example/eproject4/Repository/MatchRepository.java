package com.example.eproject4.Repository;

import com.example.eproject4.Entity.Match;
import com.example.eproject4.Entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
