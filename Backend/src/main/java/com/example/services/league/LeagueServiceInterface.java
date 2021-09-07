package com.example.services.league;

import com.example.entities.league.League;

import java.time.LocalDate;

public interface LeagueServiceInterface {
    /**
     * Randomly generate a new league instance.
     *
     * @param username   the user to generate the league for.
     * @param name       the name of the league (to be stored).
     * @param startDate  the date the league was started.
     * @param numOfTeams the number of teams to generate.
     * @param teamName   the name of the user's team.
     * @param state      the state the user's team is located in.
     * @return a new randomly generated league instance.
     */
    League newLeague(String username, String name, LocalDate startDate, int numOfTeams, String teamName, String state);

    /**
     * @param league
     * @return
     */
    int insertLeague(League league);

    /**
     * @param leagueID
     * @return
     * @throws LeagueNotFoundException
     */
    League getLeagueByID(int leagueID) throws LeagueNotFoundException;

    /**
     * @param league
     */
    void deleteLeague(League league);

    /**
     * @param leagueID
     * @throws LeagueNotFoundException
     */
    void deleteLeagueByID(int leagueID) throws LeagueNotFoundException;
}
