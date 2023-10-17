package com.example.eproject4.DTO.Response;

import com.example.eproject4.Entity.Stadium;
import com.example.eproject4.Entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchDTO {
    private Long id;
    private LocalDateTime match_time;
    private Team home_team_id;
    private Team away_team_id;
    private Stadium stadium_id;
    private Integer status;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Team home_team;
    private Team away_team;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public String getFormattedMatchTime() {
        return match_time.format(dateTimeFormatter);
    }
}
