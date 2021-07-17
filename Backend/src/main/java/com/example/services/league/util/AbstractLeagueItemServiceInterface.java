package com.example.services.league.util;

import com.example.entities.league.League;
import com.example.entities.league.LeagueItem;
import com.example.entities.league.LeagueSeason;

import java.io.Serializable;
import java.util.List;

public interface AbstractLeagueItemServiceInterface<S extends LeagueItem<Key>,
        ID extends Serializable, Ex extends LeagueItemNotFoundException,
        Key extends Serializable> {
    /**
     * @param leagueItem
     * @return
     */
    S insertLeagueItem(S leagueItem);

    /**
     * @param items
     * @return
     */
    List<S> batchInsertLeagueItems(List<S> items);

    /**
     * @param league
     * @param season
     * @return
     */
    List<S> insertAllItemsInSeason(League league, int season);

    /**
     * @param leagueSeason
     * @return
     */
    List<S> insertAllItemsInSeason(LeagueSeason leagueSeason);

    /**
     * @param league
     * @return
     */
    List<S> insertAllItemsInAllSeasons(League league);

    /**
     * @param league
     * @param identifier
     * @return
     */
    S getLeagueItemById(League league, ID identifier) throws Ex;

    /**
     * @param league
     * @param season
     * @param identifier
     * @return
     */
    S getLeagueItemById(League league, int season, ID identifier) throws Ex;

    /**
     * @param leagueSeason
     * @param identifier
     * @return
     */
    S getLeagueItemById(LeagueSeason leagueSeason, ID identifier) throws Ex;

    /**
     * @param league
     * @return
     */
    List<S> getAllItemsInSeason(League league) throws Ex;

    /**
     * @param league
     * @param season
     * @return
     */
    List<S> getAllItemsInSeason(League league, int season) throws Ex;
}
