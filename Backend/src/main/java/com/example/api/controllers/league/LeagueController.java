package com.example.api.controllers.league;

import com.example.api.controllers.league.requests.GenerateLeagueRequest;
import com.example.api.controllers.league.responses.LeagueGenerationResponse;
import com.example.api.util.ResponseBuilder;
import com.example.entities.league.League;
import com.example.services.league.LeagueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RestController
@AllArgsConstructor
@RequestMapping("/leagues")
@Api(tags = "Leagues")
public class LeagueController {
    private final LeagueService leagueService;

    @PostMapping("/{username}/new")
    @ApiOperation("Generate a new league for the given user.")
    public ResponseEntity<LeagueGenerationResponse> generateLeague(@PathVariable String username,
                                                                   @RequestBody GenerateLeagueRequest request)
            throws UsernameNotFoundException {
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
        formatter = formatter.withLocale(Locale.ENGLISH);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        return LocalDate.parse(date, formatter);
    }
}
