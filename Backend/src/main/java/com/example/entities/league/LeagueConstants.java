package com.example.entities.league;

import java.time.Month;

public class LeagueConstants {
    /*
     * The lower bound multiplier for the number of free agents in a league.
     */
    public static final int MIN_FREE_AGENTS_MULTIPLIER = 3;
    /*
     * The upper bound multiplier for the number of free agents in a league.
     */
    public static final int MAX_FREE_AGENTS_MULTIPLIER = 5;
    /*
     * The minimum number of teams in a league.
     */
    public static final int MIN_TEAMS = 10;
    /*
     * The maximum number of teams in a league.
     */
    public static final int MAX_TEAMS = 45;

    public static final int MAX_LEAGUES = 3;

    public static final Month LEAGUE_START_MONTH = Month.OCTOBER;
    public static final int LEAGUE_START_DAY = 29;
}
