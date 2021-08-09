package com.example.api.responses;

import org.springframework.http.HttpStatus;

public class LeagueGenerationResponse extends GenericResponse {
    private final int leagueID;

    public LeagueGenerationResponse(String message, int leagueID) {
        super(message);
        this.leagueID = leagueID;
    }

    public LeagueGenerationResponse(String message, HttpStatus code, int leagueID) {
        super(message, code);
        this.leagueID = leagueID;
    }

    public int getLeagueID() {
        return leagueID;
    }
}
