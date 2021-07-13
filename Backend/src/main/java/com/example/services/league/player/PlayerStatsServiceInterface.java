package com.example.services.league.player;

import com.example.entities.league.League;
import com.example.entities.league.LeagueSeason;
import com.example.entities.league.PlayerStats;

import java.util.List;

public interface PlayerStatsServiceInterface {
    /**
     *
     * @param playerStats
     * @return
     */
    PlayerStats insertPlayerStats(PlayerStats playerStats);

    /**
     *
     * @param playerStats
     * @return
     */
    List<PlayerStats> batchInsertPlayerStats(List<PlayerStats> playerStats);

    /**
     *
     * @param league
     * @return
     */
    PlayerStats insertAllPlayerStatsInAllSeasons(League league);

    /**
     *
     * @param leagueSeason
     * @return
     */
    PlayerStats insertPlayerStatsInSeason(LeagueSeason leagueSeason);

    /**
     *
     * @param league
     * @param season
     * @return
     */
    PlayerStats insertPlayerStatsInSeason(League league, int season);

    /**
     *
     * @param league
     * @param season
     * @return
     */
    PlayerStats getAllPlayerStatsInSeason(League league, int season) throws PlayerStatsNotFoundException;

    /**
     *
     * @param league
     * @return
     */
    PlayerStats getAllPlayerStatsInAllSeasons(League league) throws PlayerStatsNotFoundException;

    /**
     *
     * @param leagueSeason
     * @return
     * @throws PlayerStatsNotFoundException
     */
    PlayerStats getAllPlayerStatsInSeason(LeagueSeason leagueSeason) throws PlayerStatsNotFoundException;

    /**
     *
     * @param leagueSeason
     * @param playerID
     * @return
     * @throws PlayerStatsNotFoundException
     */
    PlayerStats getAllPlayerStatsByPlayerIDInAllSeasons(LeagueSeason leagueSeason, int playerID) throws PlayerStatsNotFoundException;
}
