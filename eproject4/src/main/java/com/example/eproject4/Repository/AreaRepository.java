package com.example.eproject4.Repository;

import com.example.eproject4.Entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AreaRepository extends JpaRepository<Area, Long>{

}