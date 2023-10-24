package com.example.eproject4.Service;

import com.example.eproject4.DTO.Response.PlayerDTO;
import com.example.eproject4.DTO.Response.TeamConclusionDTO;
import com.example.eproject4.Entity.TeamConclusion;
import com.example.eproject4.Repository.TeamConclusionRepository;
import com.example.eproject4.Utils.ModelToDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamConclusionService {
    @Autowired
    private final TeamConclusionRepository teamConclusionRepository;
    @Autowired
    private final ModelToDtoConverter modelToDtoConverter;

    public TeamConclusionService(TeamConclusionRepository teamConclusionRepository, ModelToDtoConverter modelToDtoConverter) {
        this.teamConclusionRepository = teamConclusionRepository;
        this.modelToDtoConverter = modelToDtoConverter;
    }

    public TeamConclusion create(Long teamId) throws IOException {
        TeamConclusion teamConclusion = new TeamConclusion();
        teamConclusion.setTeam_id(teamId);
        teamConclusion.setWin(0L);
        teamConclusion.setLose(0L);
        teamConclusion.setDraw(0L);
        teamConclusion.setPoint(0L);
        teamConclusion.setStatus(1);
        teamConclusion.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        teamConclusion.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        return teamConclusionRepository.save(teamConclusion);
    }

    public TeamConclusion updateTeamConclusion(Long teamId, String type) {
        TeamConclusion teamConclusion = teamConclusionRepository.findByTeam_id(teamId);

        if (teamConclusion == null) {
            return null;
        }
        if ("win".equals(type)) {
            teamConclusion.setWin(teamConclusion.getWin() + 1);
            teamConclusion.setPoint(teamConclusion.getPoint() + 3);
        } else if ("lose".equals(type)) {
            teamConclusion.setLose(teamConclusion.getLose() + 1);
            teamConclusion.setPoint(teamConclusion.getPoint());
        } else if ("draw".equals(type)) {
            teamConclusion.setDraw(teamConclusion.getDraw() + 1);
            teamConclusion.setPoint(teamConclusion.getPoint() + 1);
        }
        return teamConclusionRepository.save(teamConclusion);
    }

    public List<TeamConclusionDTO> findAllOrderByPointDesc() {
        List<TeamConclusion> teamConclusions = teamConclusionRepository.findAllOrderByPointDesc();
        return teamConclusions.stream().map(teamConclusion -> modelToDtoConverter.convertToDto(teamConclusion, TeamConclusionDTO.class)).collect(Collectors.toList());
    }
}
