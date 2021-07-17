package com.example.services.league.util;

import com.example.entities.league.League;
import com.example.entities.league.LeagueItem;
import com.example.entities.league.LeagueSeason;
import com.example.repositories.LeagueItemRepository;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
public abstract class AbstractLeagueItemService<S extends LeagueItem<Key>,
        ID extends Serializable, Ex extends LeagueItemNotFoundException, Key extends Serializable>
        implements AbstractLeagueItemServiceInterface<S, ID, Ex, Key> {

    protected final LeagueItemRepository<S, ID> repository;
    protected final String className;

    @Override
    public S insertLeagueItem(S leagueItem) {
        return repository.save(leagueItem);
    }

    @Override
    public List<S> batchInsertLeagueItems(List<S> items) {
        return Lists.newArrayList(repository.saveAll(items));
    }

    @Override
    public List<S> insertAllItemsInSeason(League league, int season) {
        LeagueSeason leagueSeason = league.getSeason(season);
        return insertAllItemsInSeason(leagueSeason);
    }

    @Override
    public List<S> insertAllItemsInSeason(LeagueSeason leagueSeason) {
        return batchInsertLeagueItems(get(className, leagueSeason));
    }

    private List<S> get(String className, LeagueSeason season) {
        switch (className) {
            case "LeagueRecord":
                return (List<S>) season.getLeagueRecords();
            case "PlayerStats":
                return (List<S>) season.getPlayerStats();
            case "LeagueStandings":
                return (List<S>) season.getLeagueStandings();
        }
        throw new RuntimeException("Unknown league item class: " + className);
    }

    @Override
    public List<S> insertAllItemsInAllSeasons(League league) {
        return null;
    }

    @Override
    public S getLeagueItemById(League league, ID identifier) throws Ex {
        return null;
    }

    @Override
    public S getLeagueItemById(League league, int season, ID identifier) throws Ex {
        return null;
    }

    @Override
    public S getLeagueItemById(LeagueSeason leagueSeason, ID identifier) throws Ex {
        return null;
    }

    @Override
    public List<S> getAllItemsInSeason(League league) throws Ex {
        return null;
    }

    @Override
    public List<S> getAllItemsInSeason(League league, int season) throws Ex {
        return null;
    }
}
