package com.example.eproject4.Repository;

import com.example.eproject4.Entity.New;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewRepository extends JpaRepository<New, Long> {
    //News Homepage
    @Query(value = "SELECT * FROM news WHERE status = 1 ORDER BY created_at DESC LIMIT 3", nativeQuery = true)
    List<New> findLatestThreeNews();

    //News Matches
    @Query(value = "SELECT * FROM news WHERE status = 1 ORDER BY created_at DESC LIMIT 2", nativeQuery = true)
    List<New> findLatestTwoNews();

    @Query(value = "SELECT * FROM news WHERE (:title is null or title like CONCAT('%',:title,'%')) "+
            " AND (:status is null or status like CONCAT('%',:status,'%')) ", nativeQuery = true)
    List<New> searchNews(@Param("title") String title, @Param("status") Integer status, Pageable pageable);


    @Query(value = "SELECT * FROM news WHERE (:title is null or title like CONCAT('%',:title,'%')) "+
            " AND (:status is null or status like CONCAT('%',:status,'%')) ", nativeQuery = true)
    List<New> searchNews1(@Param("title") String title, @Param("status") Integer status);
}
