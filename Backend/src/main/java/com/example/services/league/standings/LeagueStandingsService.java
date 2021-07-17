package com.example.services.league.standings;

import com.example.entities.league.LeagueStandings;
import com.example.repositories.LeagueStandingsRepository;
import com.example.services.league.util.AbstractLeagueItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueStandingsService extends AbstractLeagueItemService<LeagueStandings, Integer,
                LeagueStandingsNotFoundException, LeagueStandings.LeagueStandingsKey>
        implements LeagueStandingsServiceInterface {

            /**
             * 
             * @param repository
             */
    public LeagueStandingsService(@Autowired LeagueStandingsRepository repository) {
        super(repository, "LeagueStandings");
    }
}
