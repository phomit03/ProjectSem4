package com.example.eproject4.Service;

import com.example.eproject4.DTO.Request.MatchRequest;
import com.example.eproject4.DTO.Response.MatchDTO;
import com.example.eproject4.DTO.Response.TeamDTO;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Entity.Team;
import com.example.eproject4.Repository.MatchRepository;
import com.example.eproject4.Utils.Helper;
import com.example.eproject4.Utils.ModelToDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchService {
    @Autowired
    private Helper helper;
    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository, ModelToDtoConverter modelToDtoConverter) {
        this.matchRepository = matchRepository;
        this.modelToDtoConverter = modelToDtoConverter;
    }

    @Autowired
    private final ModelToDtoConverter modelToDtoConverter;

    public List<MatchDTO> getAllMatches() {
        List<Match> matches = matchRepository.findAll();

        return matches.stream().map(match -> modelToDtoConverter.convertToDto(match, MatchDTO.class))
                .collect(Collectors.toList());
    }

    public MatchDTO getMatchById(Long id) {
        Match match = matchRepository.findById(id).orElse(null);
        return modelToDtoConverter.convertToDto(match, MatchDTO.class);
    }

    public MatchDTO createMatch(MatchRequest matchRequest) {
        Match match = new Match();

        match.setDate(matchRequest.getDate());
        match.setTime(matchRequest.getTime());
        match.setStadium(matchRequest.getStadium());
        match.setStatus(1);

        match = matchRepository.save(match);
        return modelToDtoConverter.convertToDto(match, MatchDTO.class);
    }

    public Match updateMatch(MatchRequest matchRequest) {
        try {
            Match match = matchRepository.getById(matchRequest.getId());

            match.setDate(matchRequest.getDate());
            match.setTime(matchRequest.getTime());
            match.setStadium(matchRequest.getStadium());
            match.setStatus(matchRequest.getStatus());

            return matchRepository.save(match);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteMatch(Long id) {
        matchRepository.deleteById(id);
    }

}

