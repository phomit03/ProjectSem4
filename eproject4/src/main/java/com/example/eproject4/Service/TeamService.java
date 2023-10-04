package com.example.eproject4.Service;

import com.example.eproject4.DTO.Request.TeamRequest;
import com.example.eproject4.DTO.TeamDTO;
import com.example.eproject4.Entity.Team;
import com.example.eproject4.Repository.TeamRepository;
import com.example.eproject4.utils.Helper;
import com.example.eproject4.Utils.ModelToDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TeamService {
    @Autowired
    private Helper helper;
    @Autowired
    private final ModelToDtoConverter modelToDtoConverter;
    @Autowired
    private final TeamRepository teamRepository;

    public TeamService(ModelToDtoConverter modelToDtoConverter, TeamRepository teamRepository) {
        this.modelToDtoConverter = modelToDtoConverter;
        this.teamRepository = teamRepository;
    }

    public List<TeamDTO> getAllTeams() {
        List<Team> teams = teamRepository.findAll();

        return teams.stream().map(team -> modelToDtoConverter.convertToDto(team, TeamDTO.class)).collect(Collectors.toList());
    }

    public Team createTeam(TeamRequest teamRequest, MultipartFile logo) throws IOException {
        String logoPath = helper.uploadImage(logo);

        Team team = new Team();
        team.setName(teamRequest.getName());
        team.setLogo_img(logoPath);
        team.setCoach(teamRequest.getCoach());
        team.setHome_stadium(teamRequest.getStadium());
        team.setClub_valuation(teamRequest.getValuation());
        team.setStatus(teamRequest.getStatus());
        team.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        team.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));

        return teamRepository.save(team);
    }

    public TeamDTO getTeamById(Long id) {
        Team team = teamRepository.findById(id).orElse(null);
        return modelToDtoConverter.convertToDto(team, TeamDTO.class);
    }
}
