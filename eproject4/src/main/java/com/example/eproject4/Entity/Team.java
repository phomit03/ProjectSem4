package com.example.eproject4.Entity;

import com.example.eproject4.utils.MapToDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @MapToDTO
    private Long id;
    @MapToDTO
    private String name;
    @MapToDTO
    private String logo_img;
    @MapToDTO
    private String coach;
    @MapToDTO
    private String home_stadium;
    @MapToDTO
    private Float club_valuation;
    @MapToDTO
    private Integer status;
    @MapToDTO
    private Timestamp created_at;
    @MapToDTO
    private Timestamp updated_at;
}
