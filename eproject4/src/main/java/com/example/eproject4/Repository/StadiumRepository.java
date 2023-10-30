package com.example.eproject4.Repository;

import com.example.eproject4.Entity.Match;
import com.example.eproject4.Entity.Stadium;
import com.example.eproject4.Entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StadiumRepository extends JpaRepository<Stadium, Long> {
    @Query(value = "SELECT * FROM stadium WHERE (:name is null or name like CONCAT('%',:name,'%')) "+
            " AND (:status is null or status like  CONCAT('%',:status,'%'))", nativeQuery = true)
    List<Stadium> searchStadiums(@Param("name") String name, @Param("status") Integer status, Pageable pageable);

    @Query(value = "SELECT * FROM stadium WHERE (:name is null or name like CONCAT('%',:name,'%')) "+
            " AND (:status is null or status like  CONCAT('%',:status,'%'))", nativeQuery = true)
    List<Stadium> searchStadiums1(@Param("name") String name, @Param("status") Integer status);

}
