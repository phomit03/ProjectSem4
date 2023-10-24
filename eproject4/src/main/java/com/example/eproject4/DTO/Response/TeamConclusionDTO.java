package com.example.eproject4.DTO.Response;

import com.example.eproject4.Entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamConclusionDTO {
    private Long id;
    private Long team_id;
    private Long win;
    private Long lose;
    private Long draw;
    private Long point;
    private Integer status;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Team team;
}