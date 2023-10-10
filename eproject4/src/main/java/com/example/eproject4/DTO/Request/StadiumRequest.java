package com.example.eproject4.DTO.Request;

import com.example.eproject4.Entity.Stadium;
import com.example.eproject4.Entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StadiumRequest {
    private Long id;
    private String name;
    private String description;
    private Integer status;
}
