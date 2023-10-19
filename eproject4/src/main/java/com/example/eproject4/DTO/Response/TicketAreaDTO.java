package com.example.eproject4.DTO.Response;

import com.example.eproject4.Entity.Area;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Entity.Stadium;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketAreaDTO {
    private Long id;
    private Area area_id;
    private Match match_id;
    private Integer quantity;
    private Float price;
    private Integer status;
    private Timestamp created_at;
    private Timestamp updated_at;

    private Area area;
    private Match match;
}
