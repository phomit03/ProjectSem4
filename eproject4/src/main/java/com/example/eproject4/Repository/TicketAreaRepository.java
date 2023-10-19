package com.example.eproject4.Repository;

import com.example.eproject4.Entity.Employee;
import com.example.eproject4.Entity.TicketArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TicketAreaRepository extends JpaRepository<TicketArea, Long>{

}