package com.example.services.league.standings;

import com.example.services.league.util.LeagueItemNotFoundException;

public class LeagueStandingsNotFoundException extends LeagueItemNotFoundException {
    public LeagueStandingsNotFoundException(String message) {
        super(message);
    }
}
