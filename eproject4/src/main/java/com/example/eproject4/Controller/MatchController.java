package com.example.eproject4.Controller;

import com.example.eproject4.DTO.MatchDTO;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Repository.MatchRepository;
import com.example.eproject4.Service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class MatchController {
    private final MatchRepository matchRepository;


    @Autowired
    public MatchController(MatchService matchService, MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }
    @RequestMapping("/match")
    public String match(Model model) {
//        List<Match> matches = matchRepository.findAll();
//        model.addAttribute("matches", matches);
//        model.addAttribute("title", "Matches");
        return "admin_match";
    }

//    @GetMapping
//    public ResponseEntity<List<MatchDTO>> getAllMatches() {
//        List<MatchDTO> matches = matchService.getAllMatches();
//        return new ResponseEntity<>(matches, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<MatchDTO> getMatchById(@PathVariable Long id) {
//        MatchDTO match = matchService.getMatchById(id);
//        if (match != null) {
//            return new ResponseEntity<>(match, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PostMapping
//    public ResponseEntity<MatchDTO> createMatch(@RequestBody MatchDTO matchDTO) {
//        MatchDTO createdMatch = matchService.createMatch(matchDTO);
//        return new ResponseEntity<>(createdMatch, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<MatchDTO> updateMatch(@PathVariable Long id, @RequestBody MatchDTO matchDTO) {
//        MatchDTO updatedMatch = matchService.updateMatch(id, matchDTO);
//        if (updatedMatch != null) {
//            return new ResponseEntity<>(updatedMatch, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
//        matchService.deleteMatch(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}
