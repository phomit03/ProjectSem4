package com.example.eproject4.Service.impl;

import com.example.eproject4.DTO.MatchDTO;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Repository.MatchRepository;
import com.example.eproject4.Service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public List<MatchDTO> getAllMatches() {
        List<Match> matches = matchRepository.findAll();
        return matches.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MatchDTO getMatchById(Long id) {
        Match match = matchRepository.findById(id).orElse(null);
        return convertToDTO(match);
    }

    @Override
    public MatchDTO createMatch(MatchDTO matchDTO) {
        Match match = new Match();
        match.setDate(matchDTO.getDate());
        match.setTime(matchDTO.getTime());
        match.setStadium(matchDTO.getStadium());
        match.setStatus(1);

        match = matchRepository.save(match);
        return convertToDTO(match);
    }

    @Override
    public MatchDTO updateMatch(Long id, MatchDTO matchDTO) {
        Match match = matchRepository.findById(id).orElse(null);
        if (match != null) {
            match.setDate(matchDTO.getDate());
            match.setTime(matchDTO.getTime());
            match.setStadium(matchDTO.getStadium());
            match.setStatus(matchDTO.getStatus());
            match = matchRepository.save(match);
            return convertToDTO(match);
        }
        return null; // Handle the case where the match with the given id is not found
    }

    @Override
    public void deleteMatch(Long id) {
        matchRepository.deleteById(id);
    }

    private MatchDTO convertToDTO(Match match) {
        if (match == null) return null;
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(match.getId());
        matchDTO.setDate(match.getDate());
        matchDTO.setTime(match.getTime());
        matchDTO.setStadium(match.getStadium());
        matchDTO.setStatus(match.getStatus());
        return matchDTO;
    }


}
