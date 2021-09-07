package com.example.api.controllers.league.responses;

import com.example.api.util.GenericResponse;

public class LeagueGenerationResponse extends GenericResponse {
    private final int leagueID;

    public LeagueGenerationResponse(String message, int leagueID) {
        super(message);
        this.leagueID = leagueID;
    }

    public int getLeagueID() {
        return leagueID;
    }
}
