package com.example.api.controllers;

import com.example.entities.league.LeagueConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/constants")
@Api(tags="Constants")
public class GameConstantsController {

    @GetMapping("/max_leagues")
    @ApiOperation("Return the maximum number of leagues for a single user.")
    public ResponseEntity<Integer> getMaxLeagues() {
        return ResponseEntity.ok()
                .body(LeagueConstants.MAX_LEAGUES);
    }

    @GetMapping("/min_teams")
    @ApiOperation("Return the minimum number of teams in a league.")
    public ResponseEntity<Integer> getMinTeams() {
        return ResponseEntity.ok()
                .body(LeagueConstants.MIN_TEAMS);
    }

    @ApiOperation("Return the maximum number of teams in a league.")
    @GetMapping("/max_teams")
    public ResponseEntity<Integer> getMaxTeams() {
        return ResponseEntity.ok()
                .body(LeagueConstants.MAX_TEAMS);
    }
}
