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
    private Integer shot;
    private Integer shotOnTarget;
    private Integer possession;
    private Integer foul;
    private Integer passes;
    private Integer passAccuracy;
    private Integer offSide;
    private Integer corner;
    private Integer match_end;
    private Integer status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Match match;
}
