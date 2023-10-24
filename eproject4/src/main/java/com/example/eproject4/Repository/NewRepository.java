package com.example.eproject4.Repository;

import com.example.eproject4.Entity.New;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewRepository extends JpaRepository<New, Long> {
    //News Homepage
    @Query(value = "SELECT * FROM news WHERE status = 1 ORDER BY created_at DESC LIMIT 3", nativeQuery = true)
    List<New> findLatestThreeNews();

    //News Matches
    @Query(value = "SELECT * FROM news WHERE status = 1 ORDER BY created_at DESC LIMIT 2", nativeQuery = true)
    List<New> findLatestTwoNews();
}
