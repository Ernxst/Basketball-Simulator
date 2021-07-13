package com.example.services.league.player;

import com.example.entities.league.League;
import com.example.entities.league.LeagueSeason;
import com.example.entities.league.PlayerStats;
import com.example.repositories.PlayerStatsRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerStatsService implements PlayerStatsServiceInterface {
    @Autowired
    private PlayerStatsRepository playerStatsRepository;

    @Override
    public PlayerStats insertPlayerStats(PlayerStats playerStats) {
        return playerStatsRepository.save(playerStats);
    }

    @Override
    public List<PlayerStats> batchInsertPlayerStats(List<PlayerStats> playerStats) {
        return Lists.newArrayList(playerStatsRepository.saveAll(playerStats));
    }

    @Override
    public PlayerStats insertAllPlayerStatsInAllSeasons(League league) {
        return null;
    }

    @Override
    public PlayerStats insertPlayerStatsInSeason(LeagueSeason leagueSeason) {
        return null;
    }

    @Override
    public PlayerStats insertPlayerStatsInSeason(League league, int season) {
        return null;
    }

    @Override
    public PlayerStats getAllPlayerStatsInSeason(League league, int season) throws PlayerStatsNotFoundException {
        return null;
    }

    @Override
    public PlayerStats getAllPlayerStatsInAllSeasons(League league) throws PlayerStatsNotFoundException {
        return null;
    }

    @Override
    public PlayerStats getAllPlayerStatsInSeason(LeagueSeason leagueSeason) throws PlayerStatsNotFoundException {
        return null;
    }

    @Override
    public PlayerStats getAllPlayerStatsByPlayerIDInAllSeasons(LeagueSeason leagueSeason, int playerID) throws PlayerStatsNotFoundException {
        return null;
    }
}
