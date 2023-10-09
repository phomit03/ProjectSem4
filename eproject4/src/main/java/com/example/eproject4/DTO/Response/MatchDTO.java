package com.example.eproject4.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchDTO {
    private Long id;
    private Date date;
    private String time;
    private String stadium;
    private Integer status;
    private Timestamp created_at;
    private Timestamp updated_at;
}
