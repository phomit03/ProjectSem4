package com.example.eproject4.Service;

import com.example.eproject4.DTO.Response.MatchDetailEventDTO;
import com.example.eproject4.DTO.Response.PlayerDTO;
import com.example.eproject4.Entity.MatchDetailEvent;
import com.example.eproject4.Entity.New;
import com.example.eproject4.Entity.Player;
import com.example.eproject4.Repository.MatchDetailEventRepository;
import com.example.eproject4.Repository.PlayerRepository;
import com.example.eproject4.Utils.Helper;
import com.example.eproject4.Utils.ModelToDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    @Autowired
    private Helper helper;
    @Autowired
    private final ModelToDtoConverter modelToDtoConverter;
    @Autowired
    private final PlayerRepository playerRepository;
    @Autowired
    private final MatchDetailEventRepository matchDetailEventRepository;

    public PlayerService(ModelToDtoConverter modelToDtoConverter, PlayerRepository playerRepository, MatchDetailEventRepository matchDetailEventRepository) {
        this.modelToDtoConverter = modelToDtoConverter;
        this.playerRepository = playerRepository;
        this.matchDetailEventRepository = matchDetailEventRepository;
    }

    public List<PlayerDTO> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        return players.stream().map(player -> modelToDtoConverter.convertToDto(player, PlayerDTO.class)).collect(Collectors.toList());
    }

    public Player create(PlayerDTO playerDTO, MultipartFile img) throws IOException {
        Player player = new Player();
        player.setName(playerDTO.getName());
        if (!img.isEmpty()) {
            String imgUrl = helper.uploadImage(img);
            player.setAvatar_img(imgUrl);
        }
        player.setDate_of_birth(playerDTO.getDate_of_birth());
        player.setNational(playerDTO.getNational());
        player.setPosition(playerDTO.getPosition());
        player.setNumber(playerDTO.getNumber());
        player.setStatus(1);
        player.setTeam_id(playerDTO.getTeam_id());
        return playerRepository.save(player);
    }

    public PlayerDTO getPlayerById(Long id) {
        Player player = playerRepository.findById(id).orElse(null);
        return modelToDtoConverter.convertToDto(player, PlayerDTO.class);
    }

    public Player update (PlayerDTO playerDTO, MultipartFile img) {
        try {
            Player player = playerRepository.getById(playerDTO.getId());
            player.setName(playerDTO.getName());
            if (!img.isEmpty()) {
                String imgUrl = helper.uploadImage(img);
                player.setAvatar_img(imgUrl);
            }
            player.setDate_of_birth(playerDTO.getDate_of_birth());
            player.setNational(playerDTO.getNational());
            player.setPosition(playerDTO.getPosition());
            player.setNumber(playerDTO.getNumber());
            player.setWeight(playerDTO.getWeight());
            player.setHeight(playerDTO.getHeight());
            player.setAchievement(playerDTO.getAchievement());
            player.setTeam_id(playerDTO.getTeam_id());
            player.setStatus(playerDTO.getStatus());
            player.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
            return playerRepository.save(player);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PlayerDTO findById(Long id) {
        Player player = playerRepository.getById(id);
        return modelToDtoConverter.convertToDto(player, PlayerDTO.class);
    }

    public void softDelete(Long id) {
        Optional<Player> optionalEntity = playerRepository.findById(id);
        if (optionalEntity.isPresent()) {
            Player player = optionalEntity.get();
            player.setStatus(0);
            playerRepository.save(player);
        } else {
            throw new EntityNotFoundException("Entity with id " + id + " not found.");
        }
    }

    public List<PlayerDTO> findAllByTeam_id (Long id) {
        List<Player> playerList = playerRepository.findAllByTeam_id(id);
        return playerList.stream().map(player -> modelToDtoConverter.convertToDto(player, PlayerDTO.class)).collect(Collectors.toList());
    }

    public List<MatchDetailEventDTO> getEventsByPlayerIdAndMatchId(Long playerId, Long matchId) {
        List<MatchDetailEvent> matchDetailEvents = matchDetailEventRepository.findByPlayerIdAndMatchId(playerId, matchId);
        return matchDetailEvents.stream().map(matchDetailEvent -> modelToDtoConverter.convertToDto(matchDetailEvent, MatchDetailEventDTO.class)).collect(Collectors.toList());
    }

    // phan trang
    public Page<Player> findPaginated(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.playerRepository.findAll(pageable);
    }
}
