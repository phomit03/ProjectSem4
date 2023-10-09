package com.example.eproject4.DTO.Request;

import com.example.eproject4.Utils.MapToDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchRequest {
    private Long id;
    private Date date;
    private String time;
    private String stadium;
    private Integer status;
}
