package com.example.eproject4.DTO.Request;

import com.example.eproject4.Entity.Area;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Entity.Stadium;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketAreaRequest {
    private Long id;
    private Area area_id;
    private Match match_id;
    private Integer quantity;
    private Float price;
    private Integer status;
}
