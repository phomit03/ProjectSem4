package com.example.eproject4.Controller;

import com.example.eproject4.Entity.Player;
import com.example.eproject4.Service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/player")

public class PlayerController {
    private PlayerService playerService;
    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }
    // getAll
    @GetMapping
    public String listPlayers(Model model){
        List<Player> players = playerService.getAll();
        model.addAttribute("players", players);
        return "customer_players";
    }
}
