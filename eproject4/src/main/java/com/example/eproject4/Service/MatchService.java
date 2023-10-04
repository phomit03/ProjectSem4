package com.example.eproject4.Service;

import com.example.eproject4.DTO.Request.MatchRequest;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Repository.MatchRepository;
import com.example.eproject4.Utils.ModelToDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchService {
    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository, ModelToDtoConverter modelToDtoConverter) {
        this.matchRepository = matchRepository;
        this.modelToDtoConverter = modelToDtoConverter;
    }

    @Autowired
    private final ModelToDtoConverter modelToDtoConverter;

    public List<MatchRequest> getAllMatches() {
        List<Match> matches = matchRepository.findAll();

        return matches.stream().map(match -> modelToDtoConverter.convertToDto(match, MatchRequest.class)).collect(Collectors.toList());
    }

    public MatchRequest createMatch(MatchRequest matchRequest) {
        Match match = new Match();
        match.setDate(matchRequest.getDate());
        match.setTime(matchRequest.getTime());
        match.setStadium(matchRequest.getStadium());
        match.setStatus(1);

        match = matchRepository.save(match);
        return modelToDtoConverter.convertToDto(match, MatchRequest.class);
    }


}

