package com.example.eproject4.DTO.Response;

import com.example.eproject4.Entity.Match;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchDetailDTO {
    private Long id;
    private Long match_id;
    private String shot;
    private String shotOnTarget;
    private String possession;
    private String foul;
    private String passes;
    private String passAccuracy;
    private String offSide;
    private String corner;
    private String yellow_card;
    private String red_card;
    private Integer match_end;
    private Integer status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Match match;
}
