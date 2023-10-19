package com.example.eproject4.DTO.Response;

import com.example.eproject4.Entity.Area;
import com.example.eproject4.Entity.Match;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private Long id;
    private Area area;
    private Match match;
    private Integer quantity;
    private Float price;
    private Integer status;
    private Timestamp created_at;
    private Timestamp updated_at;
}
