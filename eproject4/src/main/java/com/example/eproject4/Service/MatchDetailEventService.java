package com.example.eproject4.Service;

import com.example.eproject4.DTO.Response.MatchDetailEventDTO;
import com.example.eproject4.DTO.Response.NewDTO;
import com.example.eproject4.DTO.Response.PlayerDTO;
import com.example.eproject4.Entity.MatchDetailEvent;
import com.example.eproject4.Entity.New;
import com.example.eproject4.Repository.MatchDetailEventRepository;
import com.example.eproject4.Utils.Helper;
import com.example.eproject4.Utils.ModelToDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchDetailEventService {
    @Autowired
    private final Helper helper;
    @Autowired
    private final ModelToDtoConverter modelToDtoConverter;
    @Autowired
    private final MatchDetailEventRepository matchDetailEventRepository;

    public MatchDetailEventService(Helper helper, ModelToDtoConverter modelToDtoConverter, MatchDetailEventRepository matchDetailEventRepository) {
        this.helper = helper;
        this.modelToDtoConverter = modelToDtoConverter;
        this.matchDetailEventRepository = matchDetailEventRepository;
    }

    public MatchDetailEventDTO create (MatchDetailEventDTO matchDetailEventDTO) {
        MatchDetailEvent matchDetailEvent = new MatchDetailEvent();

        matchDetailEvent.setMatch_id(matchDetailEventDTO.getMatch_id());
        matchDetailEvent.setTeam_id(matchDetailEventDTO.getTeam_id());
        matchDetailEvent.setPlayer_id(matchDetailEventDTO.getPlayer_id());
        matchDetailEvent.setType(matchDetailEventDTO.getType());
        matchDetailEvent.setTime(matchDetailEventDTO.getTime());
        matchDetailEvent.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        matchDetailEvent.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        MatchDetailEvent matchDetailEvent1 = matchDetailEventRepository.save(matchDetailEvent);
        return modelToDtoConverter.convertToDto(matchDetailEvent1, MatchDetailEventDTO.class);
    }

    public List<MatchDetailEventDTO> getEventsByPlayerIdAndMatchId(Long playerId, Long matchId) {
        List<MatchDetailEvent> events = matchDetailEventRepository.findByPlayerIdAndMatchId(playerId, matchId);
        return events.stream().map(event -> modelToDtoConverter.convertToDto(event, MatchDetailEventDTO.class)).collect(Collectors.toList());
    }

    public Long countEvent(Long teamId, Long matchId, Integer type) {
        return matchDetailEventRepository.countEventsByTeamIdAndMatchIdAndType(teamId, matchId, type);
    }

    public void delete(Long id) {
        matchDetailEventRepository.deleteById(id);
    }

    public List<MatchDetailEventDTO> getEventsByTeamIdAndMatchIdAndType(Long teamId, Long matchId, Integer type) {
        List<MatchDetailEvent> events = matchDetailEventRepository.findByMatchIdAndTeamIdAndType(teamId, matchId, type);
        return events.stream().map(event -> modelToDtoConverter.convertToDto(event, MatchDetailEventDTO.class)).collect(Collectors.toList());
    }

    public MatchDetailEventDTO findById(Long id) {
        MatchDetailEvent matchDetailEvent = matchDetailEventRepository.findByIdNew(id);
        return modelToDtoConverter.convertToDto(matchDetailEvent, MatchDetailEventDTO.class);
    }
}
