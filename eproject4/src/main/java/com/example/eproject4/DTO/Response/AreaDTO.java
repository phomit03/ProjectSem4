package com.example.eproject4.DTO.Response;

import com.example.eproject4.Entity.Stadium;
import com.example.eproject4.Entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaDTO {
    private Long id;
    private String area_name;
    private Long stadium_id;
    private Integer status;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Stadium stadium;
}
