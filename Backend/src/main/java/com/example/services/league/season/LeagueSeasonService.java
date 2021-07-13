package com.example.services.league.season;

import com.example.entities.league.League;
import com.example.entities.league.LeagueSeason;
import com.example.repositories.LeagueSeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueSeasonService implements LeagueSeasonServiceInterface {
    @Autowired
    private LeagueSeasonRepository leagueSeasonRepository;

    @Override
    public LeagueSeason insertNewSeason(League league) {
        LeagueSeason leagueSeason = league.newSeason();
        return leagueSeasonRepository.save(leagueSeason);
    }

    @Override
    public LeagueSeason insertSeason(LeagueSeason leagueSeason) {
        return leagueSeasonRepository.save(leagueSeason);
    }

    @Override
    public LeagueSeason getSeason(League league, int season) throws LeagueSeasonNotFoundException {
        return null;
    }

    @Override
    public LeagueSeason getSeason(League league) throws LeagueSeasonNotFoundException {
        return null;
    }
}
