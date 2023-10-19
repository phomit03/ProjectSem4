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
    private Long id;
    private String name;
    private String avatar_img;
    private String date_of_birth;
    private String national;
    private String position;
    private int number;
    private int weight;
    private int height;
    private String achievement;
    private Long team_id;
    private Integer status;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Team team;
}
