package com.example.eproject4.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreUpdate {
    private Long id;
    private Long match_id;
    private Long homeTeamScore;
    private Long awayTeamScore;
    private List<MatchDetailEventDTO> homeTeamEventGoal;
    private List<MatchDetailEventDTO> awayTeamEventGoal;
}
