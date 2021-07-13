package com.example.services.league.standings;

import com.example.entities.league.League;
import com.example.entities.league.LeagueSeason;
import com.example.entities.league.LeagueStandings;
import com.example.repositories.LeagueStandingsRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueStandingsService implements LeagueStandingsServiceInterface {
    @Autowired
    private LeagueStandingsRepository leagueStandingsRepository;

    @Override
    public List<LeagueStandings> batchInsertLeagueStandings(List<LeagueStandings> leagueStandings) {
        return Lists.newArrayList(leagueStandingsRepository.saveAll(leagueStandings));
    }

    @Override
    public LeagueStandings insertStandings(LeagueStandings leagueStandings) {
        return leagueStandingsRepository.save(leagueStandings);
    }

    @Override
    public List<LeagueStandings> insertAllStandingsInSeason(League league, int season) {
        LeagueSeason leagueSeason = league.getSeason(season);
        return insertAllStandingsInSeason(leagueSeason);
    }

    @Override
    public List<LeagueStandings> insertAllStandingsInSeason(LeagueSeason leagueSeason) {
        return batchInsertLeagueStandings(leagueSeason.getLeagueStandings());
    }

    @Override
    public List<LeagueStandings> insertAllStandingsInAllSeasons(League league) {
        return null;
    }

    @Override
    public LeagueStandings getStandings(League league, int season) throws LeagueStandingsNotFoundException {
        return null;
    }

    @Override
    public LeagueStandings getStandings(League league) throws LeagueStandingsNotFoundException {
        return null;
    }
}
