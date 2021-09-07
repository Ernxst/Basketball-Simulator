package com.example.services.league.season;

import com.example.entities.league.League;
import com.example.entities.league.LeagueSeason;

public interface LeagueSeasonServiceInterface {
    /**
     * @param league
     * @return
     */
    LeagueSeason insertNewSeason(League league);

    /**
     * @param leagueSeason
     * @return
     */
    LeagueSeason insertSeason(LeagueSeason leagueSeason);

    /**
     * @param league
     * @param season
     * @return
     */
    LeagueSeason getSeason(League league, int season);


    /**
     * @param league
     * @return
     */
    LeagueSeason getSeason(League league);
}
