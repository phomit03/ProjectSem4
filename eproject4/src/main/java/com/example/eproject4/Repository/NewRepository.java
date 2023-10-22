package com.example.eproject4.Repository;

import com.example.eproject4.Entity.New;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewRepository extends JpaRepository<New, Long> {
    @Query(value = "SELECT * FROM news WHERE status = 0 ORDER BY created_at ASC LIMIT 3", nativeQuery = true)
    List<New> findLatestThreeNews();
}
