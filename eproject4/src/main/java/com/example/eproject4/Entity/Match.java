package com.example.eproject4.Entity;

import com.example.eproject4.utils.MapToDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @MapToDTO
    private int id;
    @MapToDTO
    private Date date;
    @MapToDTO
    private Time time;
    @MapToDTO
    private String stadium;
    @MapToDTO
    private Integer status;
    @MapToDTO
    private Timestamp created_at;
    @MapToDTO
    private Timestamp updated_at;
}