package com.example.services.league.standings;

import com.example.entities.league.League;
import com.example.entities.league.LeagueSeason;
import com.example.entities.league.LeagueStandings;

import java.util.List;

public interface LeagueStandingsServiceInterface {
    /**
     *
     * @return
     */
    List<LeagueStandings> batchInsertLeagueStandings(List<LeagueStandings> leagueStandings);
    /**
     *
     * @param leagueStandings
     * @return
     */
    LeagueStandings insertStandings(LeagueStandings leagueStandings);

    /**
     *
     * @param league
     * @param season
     * @return
     */
    List<LeagueStandings> insertAllStandingsInSeason(League league, int season);

    /**
     *
     * @param leagueSeason
     * @return
     */
    List<LeagueStandings> insertAllStandingsInSeason(LeagueSeason leagueSeason);

    /**
     * @param league
     * @return
     */
    List<LeagueStandings> insertAllStandingsInAllSeasons(League league);


    /**
     * @param league
     * @param season
     * @return
     */
    LeagueStandings getStandings(League league, int season) throws LeagueStandingsNotFoundException;


    /**
     * @param league
     * @return
     */
    LeagueStandings getStandings(League league) throws LeagueStandingsNotFoundException;
}
