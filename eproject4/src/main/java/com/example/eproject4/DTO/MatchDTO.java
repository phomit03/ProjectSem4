package com.example.eproject4.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchDTO {
    private int id;
    private Date date;
    private Time time;
    private String stadium;
    private Integer status;
    private Timestamp created_at;
    private Timestamp updated_at;
}
