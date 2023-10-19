package com.example.eproject4.DTO.Request;

import com.example.eproject4.Entity.Area;
import com.example.eproject4.Entity.Match;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {
    private Long id;
    private Area area_id;
    private Match match_id;
    private Integer quantity;
    private Float price;
    private Integer status;
}
