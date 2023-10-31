package com.example.eproject4.Service;

import com.example.eproject4.DTO.Response.MatchDetailDTO;
import com.example.eproject4.Entity.MatchDetail;
import com.example.eproject4.Repository.MatchDetailRepository;
import com.example.eproject4.Utils.Helper;
import com.example.eproject4.Utils.ModelToDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchDetailService {
    @Autowired
    private final MatchDetailRepository matchDetailRepository;
    @Autowired
    private final ModelToDtoConverter modelToDtoConverter;

    public MatchDetailService(MatchDetailRepository matchDetailRepository, ModelToDtoConverter modelToDtoConverter) {
        this.matchDetailRepository = matchDetailRepository;
        this.modelToDtoConverter = modelToDtoConverter;
    }

    public MatchDetailDTO getMatchDetailByMatchId(Long id) {
        MatchDetail matchDetail = matchDetailRepository.findMatchDetailByMatchId(id);
        return modelToDtoConverter.convertToDto(matchDetail, MatchDetailDTO.class);
    }

    public MatchDetail updateMatchDetail(MatchDetailDTO matchDetailDTO) {
        MatchDetail matchDetail = matchDetailRepository.findMatchDetailByMatchId(matchDetailDTO.getMatch_id());
        if (matchDetail != null) {
            matchDetail.setShot(matchDetailDTO.getShot());
            matchDetail.setShotOnTarget(matchDetailDTO.getShotOnTarget());
            matchDetail.setPossession(matchDetailDTO.getPossession());
            matchDetail.setFoul(matchDetailDTO.getFoul());
            matchDetail.setYellow_card(matchDetailDTO.getYellow_card());
            matchDetail.setRed_card(matchDetailDTO.getRed_card());
            matchDetail.setPasses(matchDetailDTO.getPasses());
            matchDetail.setPassAccuracy(matchDetailDTO.getPassAccuracy());
            matchDetail.setOffSide(matchDetailDTO.getOffSide());
            matchDetail.setCorner(matchDetailDTO.getCorner());
            matchDetail.setMatch_end(matchDetailDTO.getMatch_end());
            return matchDetailRepository.save(matchDetail);
        }
        return null;
    }
}
