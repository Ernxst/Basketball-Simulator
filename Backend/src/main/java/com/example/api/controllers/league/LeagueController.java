package com.example.api.controllers.league;

import com.example.api.responses.GenericResponse;
import com.example.entities.league.League;
import com.example.services.league.LeagueService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RestController
@AllArgsConstructor
@RequestMapping("/league")
public class LeagueController {
    private final LeagueService leagueService;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Data
    private static class GenerateLeagueRequest {
        private String username;
        private String leagueName;
        private String startDate;
        private int numOfTeams;
        private String teamState;
        private String teamName;
    }

    @PostMapping("/generate")
    public ResponseEntity<GenericResponse> generateLeague(@RequestBody GenerateLeagueRequest request) {
        String username = request.getUsername();
        String leagueName = request.getLeagueName();
        String startDate = request.getStartDate();
        int numOfTeams = request.getNumOfTeams();
        String teamState = request.getTeamState();
        String teamName = request.getTeamName();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale( Locale.ENGLISH );  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        LocalDate date = LocalDate.parse(startDate, formatter);

        try {
            League league = leagueService.newLeague(username, leagueName, date, numOfTeams, teamName, teamState);
            return ResponseEntity.ok()
                    .body(new GenericResponse("Success", HttpStatus.OK));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT.value())
                    .body(new GenericResponse(e.getMessage(), HttpStatus.CONFLICT));
        }
    }
}
