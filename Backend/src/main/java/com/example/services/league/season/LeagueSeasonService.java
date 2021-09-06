package com.example.services.league.season;

import com.example.entities.league.League;
import com.example.entities.league.LeagueSeason;
import com.example.repositories.LeagueSeasonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LeagueSeasonService implements LeagueSeasonServiceInterface {
    private final LeagueSeasonRepository leagueSeasonRepository;

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
    public LeagueSeason getSeason(League league, int season) {
        return null;
    }

    @Override
    public LeagueSeason getSeason(League league) {
        return null;
    }
}
