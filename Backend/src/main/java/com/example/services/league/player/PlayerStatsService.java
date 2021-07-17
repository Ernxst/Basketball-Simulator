package com.example.services.league.player;

import com.example.entities.league.PlayerStats;
import com.example.repositories.PlayerStatsRepository;
import com.example.services.league.util.AbstractLeagueItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerStatsService extends AbstractLeagueItemService<PlayerStats, Integer,
        PlayerStatsNotFoundException, PlayerStats.PlayerStatsKey>
        implements PlayerStatsServiceInterface {

    public PlayerStatsService(@Autowired PlayerStatsRepository repository) {
        super(repository, "PlayerStats");
    }
}
