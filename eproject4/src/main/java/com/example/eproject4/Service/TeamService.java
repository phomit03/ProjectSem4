package com.example.eproject4.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.eproject4.DTO.Response.TeamDTO;
import com.example.eproject4.Entity.Team;
import com.example.eproject4.Repository.TeamRepository;
import com.example.eproject4.Utils.Helper;
import com.example.eproject4.Utils.ModelToDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
        team.setCoach(teamDTO.getCoach());
        team.setHome_stadium(teamDTO.getHome_stadium());
        team.setClub_valuation(teamDTO.getClub_valuation());
        team.setStatus(1);
        if (!logo.isEmpty()) {
            String imageUrl = helper.uploadImage(logo);
            team.setLogo_img(imageUrl);
        }
        return teamRepository.save(team);
    }

    public TeamDTO getTeamById(Long id) {
        Team team = teamRepository.findById(id).orElse(null);
        return modelToDtoConverter.convertToDto(team, TeamDTO.class);
    }

    public Team update (TeamDTO teamDTO, MultipartFile logo) {
        try {
            Team team = teamRepository.getById(teamDTO.getId());
            if (!logo.isEmpty()) {
                String logo_img = helper.uploadImage(logo);
                team.setLogo_img(logo_img);
            }
            team.setName(teamDTO.getName());
            team.setCoach(teamDTO.getCoach());
            team.setHome_stadium(teamDTO.getHome_stadium());
            team.setClub_valuation(teamDTO.getClub_valuation());
            team.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
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
