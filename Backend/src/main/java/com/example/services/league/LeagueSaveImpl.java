package com.example.services.league;

import java.time.LocalDate;

import com.example.util.LocalDateJson;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LeagueSaveImpl implements LeagueSave {
    private final int leagueID;
    private final String leagueName;
    private final String teamState;
    private final String teamName;
    private final int iconID;

    @LocalDateJson
    private final LocalDate currentDate;

    private final int currentSeason;

    @LocalDateJson
    private final LocalDate lastPlayed;

    private final int wins;
    private final int losses;
}
