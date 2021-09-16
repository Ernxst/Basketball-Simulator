package com.example.api.controllers.league;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import com.example.api.controllers.league.requests.GenerateLeagueRequest;
import com.example.api.controllers.league.responses.LeagueGenerationResponse;
import com.example.api.controllers.league.responses.LeagueSavesResponse;
import com.example.api.util.ResponseBuilder;
import com.example.entities.league.League;
import com.example.services.league.LeagueSave;
import com.example.services.league.LeagueService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/{username}/leagues")
@Api(tags = "Leagues")
public class LeagueController {
    private final LeagueService leagueService;

    @PostMapping(value = "/new", consumes = "application/json", produces = "application/json")
    @ApiOperation("Generate a new league for the given user.")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<LeagueGenerationResponse> generateLeague(@PathVariable String username,
            @RequestBody GenerateLeagueRequest request) throws UsernameNotFoundException {
        String leagueName = request.getLeagueName();
        String startDate = request.getStartDate();
        int numOfTeams = request.getNumOfTeams();
        String teamState = request.getTeamState();
        String teamName = request.getTeamName();

        LocalDate date = toLocalDate(startDate);
        League league = leagueService.newLeague(username, leagueName, date, numOfTeams, teamName, teamState);
        LeagueGenerationResponse body = new LeagueGenerationResponse("Success", league.getLeagueID());
        return new ResponseBuilder<>(HttpStatus.CREATED, body).build();
    }

    private LocalDate toLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.ENGLISH);
        return LocalDate.parse(date, formatter);
    }

    @GetMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation("Return all league saves for a given user account.")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<LeagueSavesResponse> getSaves(@PathVariable String username)
            throws UsernameNotFoundException {
        List<LeagueSave> saves = leagueService.getLeagueSaves(username);
        LeagueSavesResponse body = new LeagueSavesResponse(saves);
        return new ResponseBuilder<>(HttpStatus.OK, body).build();
    }
}
