package com.example.services.league.record;

import com.example.services.league.util.LeagueItemNotFoundException;

public class LeagueRecordNotFoundException extends LeagueItemNotFoundException {
    public LeagueRecordNotFoundException(String message) {
        super(message);
    }
}
