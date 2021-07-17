package com.example.services.league.player;

import com.example.services.league.util.LeagueItemNotFoundException;

public class PlayerStatsNotFoundException extends LeagueItemNotFoundException {
    public PlayerStatsNotFoundException(String message) {
        super(message);
    }
}
