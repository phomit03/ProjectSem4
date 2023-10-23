package com.example.eproject4.Repository;

import com.example.eproject4.Entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AreaRepository extends JpaRepository<Area, Long>{
    @Query("SELECT e FROM Area e WHERE e.id = :matchIdValue and e.status = 1")
    Area findByAreaById(@Param("matchIdValue") int id);
}