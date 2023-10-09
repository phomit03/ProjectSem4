package com.example.eproject4.DTO.Response;

import com.example.eproject4.Utils.MapToDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
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