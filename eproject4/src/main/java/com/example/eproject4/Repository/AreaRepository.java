package com.example.eproject4.Repository;

import com.example.eproject4.Entity.Area;
import com.example.eproject4.Entity.Employee;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AreaRepository extends JpaRepository<Area, Long>{
    @Query("SELECT e FROM Area e WHERE e.id = :matchIdValue")
    Area findByAreaById(@Param("matchIdValue") int id);
}