package com.example.eproject4.Service;

import com.example.eproject4.DTO.Request.TeamRequest;
import com.example.eproject4.DTO.TeamDTO;
import com.example.eproject4.Entity.Team;
import com.example.eproject4.Repository.TeamRepository;
import com.example.eproject4.Utils.Helper;
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
import java.util.Base64;
import java.util.List;
import java.util.Optional;
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

    public Team createTeam(TeamDTO teamDTO, MultipartFile logo) throws IOException {
        Team team = new Team();
        team.setName(teamDTO.getName());
        if (logo == null) {
            team.setLogo_img(teamDTO.getLogo_img());
        } else {
            Object[] uploadResult = helper.uploadImage(logo);
            if ((boolean) uploadResult[0]) {
                team.setLogo_img((String) uploadResult[1]);
            }
        }
        team.setCoach(teamDTO.getCoach());
        team.setHome_stadium(teamDTO.getHome_stadium());
        team.setClub_valuation(teamDTO.getClub_valuation());
        team.setStatus(teamDTO.getStatus());
        team.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        team.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));

        return teamRepository.save(team);
    }

    public TeamDTO getTeamById(Long id) {
        Team team = teamRepository.findById(id).orElse(null);
        return modelToDtoConverter.convertToDto(team, TeamDTO.class);
    }

    public Team update (TeamDTO teamDTO, MultipartFile logo) {
        try {
            System.out.println(logo);
            Team team = teamRepository.getById(teamDTO.getId());
            if (logo.isEmpty()) {
                team.setLogo_img(teamDTO.getLogo_img());
            } else {
                Object[] uploadResult = helper.uploadImage(logo);
                if ((boolean) uploadResult[0]) {
                    team.setLogo_img((String) uploadResult[1]);
                }
            }
            team.setName(teamDTO.getName());
            team.setCoach(teamDTO.getCoach());
            team.setHome_stadium(teamDTO.getHome_stadium());
            team.setClub_valuation(teamDTO.getClub_valuation());
            team.setStatus(teamDTO.getStatus());
            return teamRepository.save(team);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public TeamDTO findById(Long id) {
        Team team = teamRepository.getById(id);
        return modelToDtoConverter.convertToDto(team, TeamDTO.class);
    }

    public void delete(Long id) {
        teamRepository.deleteById(id);
    }
}
