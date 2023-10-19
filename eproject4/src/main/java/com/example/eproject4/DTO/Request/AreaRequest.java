package com.example.eproject4.DTO.Request;

import com.example.eproject4.Entity.Stadium;
import com.example.eproject4.Entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaRequest {
    private Long id;
    private String area_name;
    private Stadium stadium_id;
    private Integer status;
}
