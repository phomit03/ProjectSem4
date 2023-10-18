package com.example.eproject4.DTO.Response;

import com.example.eproject4.Entity.Stadium;
import com.example.eproject4.Entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StadiumDTO {
    private Long id;
    private String name;
    private String map_img;
    private String description;
    private Integer status;
    private Timestamp created_at;
    private Timestamp updated_at;
}
