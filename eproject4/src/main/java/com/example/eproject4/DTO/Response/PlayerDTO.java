package com.example.eproject4.DTO.Response;

import com.example.eproject4.Entity.Team;
import com.example.eproject4.Utils.MapToDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {
    @MapToDTO
    private Long id;
    @MapToDTO
    private String name;
    @MapToDTO
    private String avatar_img;
    @MapToDTO
    private String date_of_birth;
    @MapToDTO
    private String national;
    @MapToDTO
    private String position;
    @MapToDTO
    private int number;
    @MapToDTO
    private int weight;
    @MapToDTO
    private int height;
    @MapToDTO
    private String achievement;
    @MapToDTO
    private Long team_id;
    @MapToDTO
    private Integer status;
    @MapToDTO
    private Timestamp created_at;
    @MapToDTO
    private Timestamp updated_at;
    @MapToDTO
    private Team team;
}
