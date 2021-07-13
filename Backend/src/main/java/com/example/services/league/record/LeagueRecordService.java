package com.example.services.league.record;

import com.example.entities.league.League;
import com.example.entities.league.LeagueRecord;
import com.example.entities.league.LeagueSeason;
import com.example.repositories.LeagueRecordRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueRecordService implements LeagueRecordServiceInterface {
    @Autowired
    private LeagueRecordRepository leagueRecordRepository;

    @Override
    public List<LeagueRecord> batchInsertLeagueRecords(List<LeagueRecord> records) {
        return Lists.newArrayList(leagueRecordRepository.saveAll(records));
    }

    @Override
    public List<LeagueRecord> insertAllRecordsInSeason(League league, int season) {
        LeagueSeason leagueSeason = league.getSeason(season);
        return insertAllRecordsInSeason(leagueSeason);
    }

    @Override
    public List<LeagueRecord> insertAllRecordsInSeason(LeagueSeason leagueSeason) {
        return batchInsertLeagueRecords(leagueSeason.getLeagueRecords());
    }

    @Override
    public List<LeagueRecord> insertAllRecordsInAllSeasons(League league) {
        return null;
    }

    @Override
    public LeagueRecord insertLeagueRecord(LeagueRecord leagueRecord) {
        return leagueRecordRepository.save(leagueRecord);
    }

    @Override
    public LeagueRecord getLeagueRecordByTitle(League league, String title) throws LeagueRecordNotFoundException {
        return null;
    }

    @Override
    public LeagueRecord getLeagueRecordByTitle(League league, int season, String title) throws LeagueRecordNotFoundException {
        return null;
    }

    @Override
    public LeagueRecord getLeagueRecordByTitle(LeagueSeason leagueSeason, String title) throws LeagueRecordNotFoundException {
        return null;
    }

    @Override
    public List<LeagueRecord> getAllRecordsInSeason(League league) throws LeagueRecordNotFoundException {
        return null;
    }

    @Override
    public List<LeagueRecord> getAllRecordsInSeason(League league, int season) throws LeagueRecordNotFoundException {
        return null;
    }
}
