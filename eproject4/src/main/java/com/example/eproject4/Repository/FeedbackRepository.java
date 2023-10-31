package com.example.eproject4.Repository;

import com.example.eproject4.Entity.Feedback;
import com.example.eproject4.Entity.Stadium;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query(value = "SELECT * FROM feedbacks WHERE (:name is null or name like CONCAT('%',:name,'%')) "+
            " AND (:email is null or email like  CONCAT('%',:email,'%'))", nativeQuery = true)
    List<Feedback> searchFeedbacks(@Param("name") String name, @Param("email") String email, Pageable pageable);

    @Query(value = "SELECT * FROM feedbacks WHERE (:name is null or name like CONCAT('%',:name,'%')) "+
            " AND (:email is null or email like  CONCAT('%',:email,'%'))", nativeQuery = true)
    List<Feedback> searchFeedbacks1(@Param("name") String name, @Param("email") String email);



}
