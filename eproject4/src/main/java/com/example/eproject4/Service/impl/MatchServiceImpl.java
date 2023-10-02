package com.example.eproject4.Service.impl;

import com.example.eproject4.DTO.MatchDTO;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Repository.MatchRepository;
import com.example.eproject4.Service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        List<MatchDTO> matchDTOs = new ArrayList<>();

        for (Match match : matches) {
            matchDTOs.add(convertToDTO(match));
        }

        return matchDTOs;
    }

    @Override
    public MatchDTO getMatchById(Long id) {
        Match match = matchRepository.findById(id).orElse(null);

        if (match != null) {
            return convertToDTO(match);
        } else {
            return null;
        }
    }

    @Override
    public MatchDTO createMatch(MatchDTO matchDTO) {
        Match match = convertToEntity(matchDTO);
        match = matchRepository.save(match);
        return convertToDTO(match);
    }

    @Override
    public MatchDTO updateMatch(Long id, MatchDTO matchDTO) {
        Match existingMatch = matchRepository.findById(id).orElse(null);

        if (existingMatch != null) {
            // Cập nhật thông tin từ matchDTO vào existingMatch
            existingMatch.setDate(matchDTO.getDate());
            existingMatch.setTime(matchDTO.getTime());
            existingMatch.setStadium(matchDTO.getStadium());
            existingMatch.setStatus(matchDTO.getStatus());

            existingMatch = matchRepository.save(existingMatch);
            return convertToDTO(existingMatch);
        } else {
            return null;
        }
    }

    @Override
    public void deleteMatch(Long id) {
        matchRepository.deleteById(id);
    }

    private MatchDTO convertToDTO(Match match) {
        return new MatchDTO(
            match.getId(),
            match.getDate(),
            match.getTime(),
            match.getStadium(),
            match.getStatus(),
            match.getCreated_at(),
            match.getUpdated_at()
        );
    }

    private Match convertToEntity(MatchDTO matchDTO) {
        return new Match(
            matchDTO.getId(),
            matchDTO.getDate(),
            matchDTO.getTime(),
            matchDTO.getStadium(),
            matchDTO.getStatus(),
            matchDTO.getCreated_at(),
            matchDTO.getUpdated_at()
        );
    }
}
