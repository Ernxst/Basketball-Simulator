package com.example.services.league.record;

import com.example.entities.league.League;
import com.example.entities.league.LeagueRecord;
import com.example.entities.league.LeagueSeason;

import java.util.List;

public interface LeagueRecordServiceInterface {
    /**
     *
     * @param records
     * @return
     */
    List<LeagueRecord> batchInsertLeagueRecords(List<LeagueRecord> records);

    /**
     *
     * @param league
     * @param season
     * @return
     */
    List<LeagueRecord> insertAllRecordsInSeason(League league, int season);

    /**
     *
     * @param leagueSeason
     * @return
     */
    List<LeagueRecord> insertAllRecordsInSeason(LeagueSeason leagueSeason);

    /**
     *
     * @param league
     * @return
     */
    List<LeagueRecord> insertAllRecordsInAllSeasons(League league);

    /**
     *
     * @param leagueRecord
     * @return
     */
    LeagueRecord insertLeagueRecord(LeagueRecord leagueRecord);

    /**
     *
     * @param league
     * @param title
     * @return
     */
    LeagueRecord getLeagueRecordByTitle(League league, String title) throws LeagueRecordNotFoundException;

    /**
     *
     * @param league
     * @param season
     * @param title
     * @return
     */
    LeagueRecord getLeagueRecordByTitle(League league, int season, String title) throws LeagueRecordNotFoundException;

    /**
     *
     * @param leagueSeason
     * @param title
     * @return
     */
    LeagueRecord getLeagueRecordByTitle(LeagueSeason leagueSeason, String title) throws LeagueRecordNotFoundException;

    /**
     *
     * @param league
     * @return
     */
    List<LeagueRecord> getAllRecordsInSeason(League league) throws LeagueRecordNotFoundException;

    /**
     *
     * @param league
     * @param season
     * @return
     */
    List<LeagueRecord> getAllRecordsInSeason(League league, int season)  throws LeagueRecordNotFoundException;

}
