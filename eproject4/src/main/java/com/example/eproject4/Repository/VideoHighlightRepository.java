package com.example.eproject4.Repository;

import com.example.eproject4.Entity.New;
import com.example.eproject4.Entity.VideoHighlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoHighlightRepository extends JpaRepository<VideoHighlight, Long> {
    @Query(value = "SELECT * FROM video_highlight WHERE status = 1 ORDER BY created_at DESC LIMIT 12", nativeQuery = true)
    List<VideoHighlight> findVideoHightlightNew();
}
