package com.example.eproject4.Service;

import com.example.eproject4.DTO.MatchDTO;

import java.text.ParseException;
import java.util.List;

public interface MatchService {
    List<MatchDTO> getAllMatches();
    MatchDTO getMatchById(Long id);
    MatchDTO createMatch(MatchDTO matchDTO);
    MatchDTO updateMatch(Long id, MatchDTO matchDTO);
    void deleteMatch(Long id);
}
