package com.example.api.controllers.constants;

import com.example.api.controllers.constants.responses.TeamNamesResponse;
import com.example.api.controllers.constants.responses.TeamStatesResponse;
import com.example.api.util.ResponseBuilder;
import com.example.entities.league.LeagueConstants;
import com.example.services.NameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/constants")
@Api(tags = "Constants")
@AllArgsConstructor
public class GameConstantsController {
    private final NameService nameService;

    @GetMapping(value = "/max_leagues", produces = "application/json")
    @ApiOperation("Return the maximum number of leagues for a single user.")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Integer> getMaxLeagues() {
        return ResponseEntity.ok().body(LeagueConstants.MAX_LEAGUES);
    }

    @GetMapping(value = "/min_teams", produces = "application/json")
    @ApiOperation("Return the minimum number of teams in a league.")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Integer> getMinTeams() {
        return ResponseEntity.ok().body(LeagueConstants.MIN_TEAMS);
    }

    @GetMapping(value = "/max_teams", produces = "application/json")
    @ApiOperation("Return the maximum number of teams in a league.")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Integer> getMaxTeams() {
        return ResponseEntity.ok().body(LeagueConstants.MAX_TEAMS);
    }

    @GetMapping(value = "/team_names", produces = "application/json")
    @ApiOperation("Return all available team names.")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<TeamNamesResponse> getTeamNames() {
        List<String> teamNames = nameService.getTeamNames();
        TeamNamesResponse response = new TeamNamesResponse(teamNames);
        return new ResponseBuilder<>(HttpStatus.OK, response).build();
    }

    @GetMapping(value = "/team_states", produces = "application/json")
    @ApiOperation("Return all available team USA states.")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<TeamStatesResponse> getTeamStates() {
        List<String> teamStates = nameService.getTeamStates();
        TeamStatesResponse response = new TeamStatesResponse(teamStates);
        return new ResponseBuilder<>(HttpStatus.OK, response).build();
    }
}
