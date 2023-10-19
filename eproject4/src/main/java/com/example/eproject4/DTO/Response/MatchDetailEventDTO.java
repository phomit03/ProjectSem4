package com.example.eproject4.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchDetailEventDTO {
    private Long id;
    private Long match_id;
    private Long team_id;
    private Long player_id;
    private Integer type;
    private String time;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
