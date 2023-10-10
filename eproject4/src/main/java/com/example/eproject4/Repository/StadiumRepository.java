package com.example.eproject4.Repository;

import com.example.eproject4.Entity.Match;
import com.example.eproject4.Entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StadiumRepository extends JpaRepository<Stadium, Long> {
}
