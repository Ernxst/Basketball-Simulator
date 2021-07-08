package com.example.api.controllers;

import com.example.entities.league.LeagueConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/constants")
public class GameConstantsController {

    @GetMapping("/max-leagues")
    public ResponseEntity<Integer> getMaxLeagues() {
        return ResponseEntity.ok()
                .body(LeagueConstants.MAX_LEAGUES);
    }

    @GetMapping("/min-teams")
    public ResponseEntity<Integer> getMinTeams() {
        return ResponseEntity.ok()
                .body(LeagueConstants.MIN_TEAMS);
    }

    @GetMapping("/max-teams")
    public ResponseEntity<Integer> getMaxTeams() {
        return ResponseEntity.ok()
                .body(LeagueConstants.MAX_TEAMS);
    }
}
