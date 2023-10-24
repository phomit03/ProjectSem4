package com.example.eproject4.Service;

import com.example.eproject4.DTO.Request.MatchRequest;
import com.example.eproject4.DTO.Response.MatchDTO;
import com.example.eproject4.DTO.Response.TeamDTO;
import com.example.eproject4.Entity.*;
import com.example.eproject4.Repository.MatchDetailRepository;
import com.example.eproject4.Repository.MatchRepository;
import com.example.eproject4.Utils.Helper;
import com.example.eproject4.Utils.ModelToDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchService {
    @Autowired
    private Helper helper;
    @Autowired
    private final MatchRepository matchRepository;
    @Autowired
    private MatchDetailService matchDetailService;
    @Autowired
    private MatchDetailRepository matchDetailRepository;
    @Autowired
    private final ModelToDtoConverter modelToDtoConverter;

    @Autowired
    public MatchService(MatchRepository matchRepository, ModelToDtoConverter modelToDtoConverter) {
        this.matchRepository = matchRepository;
        this.modelToDtoConverter = modelToDtoConverter;
    }

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

        match.setMatch_time(LocalDateTime.parse(matchRequest.getMatch_time()));
        match.setHome_team_id(matchRequest.getHome_team_id());
        match.setAway_team_id(matchRequest.getAway_team_id());
        match.setStadium_id(matchRequest.getStadium_id());
        match.setStatus(1);

        match = matchRepository.save(match);

        MatchDetail matchDetail = new MatchDetail();
        matchDetail.setMatch_id(match.getId());
        matchDetail.setMatch_end(0);
        matchDetailRepository.save(matchDetail);
        return modelToDtoConverter.convertToDto(match, MatchDTO.class);
    }

    public Match updateMatch(MatchRequest matchRequest) {
        try {
            Match match = matchRepository.getById(matchRequest.getId());

            match.setMatch_time(LocalDateTime.parse(matchRequest.getMatch_time()));
            match.setHome_team_id(matchRequest.getHome_team_id());
            match.setAway_team_id(matchRequest.getAway_team_id());
            match.setStadium_id(matchRequest.getStadium_id());
            match.setStatus(matchRequest.getStatus());
            match.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));

            return matchRepository.save(match);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void softDelete(Long id) {
        Optional<Match> optionalEntity = matchRepository.findById(id);
        if (optionalEntity.isPresent()) {
            Match match = optionalEntity.get();
            match.setStatus(0);
            matchRepository.save(match);
        } else {
            throw new EntityNotFoundException("Entity with id " + id + " not found.");
        }
    }

    public String formatDateTime(String dateTimeStr) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        LocalDateTime inputDateTime = LocalDateTime.parse(dateTimeStr, inputFormatter);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return inputDateTime.format(outputFormatter);
    }

    public boolean checkMatchExist(MatchRequest matchRequest) {
        //check time va stadium
        if (!matchRepository.findMatchesByMatchTimeAndStadiumId(LocalDateTime.parse(matchRequest.getMatch_time()), matchRequest.getStadium_id().getId()).isEmpty()) {
            return false;
        }
        if (!matchRepository.findMatchesByMatchTimeAndHomeTeamId(LocalDateTime.parse(matchRequest.getMatch_time()), matchRequest.getHome_team_id().getId()).isEmpty()) {
            return false;
        }
        if (!matchRepository.findMatchesByMatchTimeAndAwayTeamId(LocalDateTime.parse(matchRequest.getMatch_time()), matchRequest.getAway_team_id().getId()).isEmpty()) {
            return false;
        }

        return true;
    }

    public List<MatchDTO> getNextMatchesOrLiveMatches(int limit) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<Match> matches;
        List<Match> liveMatches = matchRepository.findLiveMatches(currentDateTime);
        int typeMatch = liveMatches.isEmpty() ? 2 : 1;

        if (liveMatches.isEmpty()) {
            matches = matchRepository.findLatestFinishedMatch();
        } else {
            matches = liveMatches;
        }

        int maxResults = limit != 0 ? limit : 3;
        if (matches.size() > maxResults) {
            matches = matches.subList(0, maxResults);
        }

        return matches.stream().map(match -> {
            MatchDTO dto = modelToDtoConverter.convertToDto(match, MatchDTO.class);
            dto.setType_match(typeMatch);
            return dto;
        }).collect(Collectors.toList());
    }

    //list tran dau da ket thuc
    public List<Match> findAllFinishedMatches() {
        return matchRepository.findAllFinishedMatches();
    }

    // Lấy 3 kết quả gần nhất da ket thuc
    public List<Match> findLatestFinishedMatches() {
        Pageable pageable = PageRequest.of(0, 3);
        return matchRepository.findLatestFinishedMatches(pageable);
    }

    //Next Match (random)
    public List<Match> getFindNextMatch() {
        return matchRepository.findNextMatch();
    }

    //Upcoming homepage
    /*public List<Match> getUpComingHomePage() {
        return matchRepository.find6UpComingMatches();
    }*/

    // phan trang
    public Page<Match> findPaginated(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.matchRepository.findAll(pageable);
    }
}

