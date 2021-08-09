package com.example.services.team;


import com.example.entities.team.Team;

import java.util.Collection;

public interface TeamServiceInterface {
    /**
     *
     * @return
     */
    int randomTeamIconID();

    /**
     *
     * @param team
     * @return
     */
    int insertTeam(Team team);

    /**
     *
     * @param teams
     * @return
     */
    Iterable<Team> insertTeams(Collection<Team> teams);

    /**
     *
     * @param teamID
     * @return
     * @throws TeamNotFoundException
     */
    Team getTeamByID(int teamID) throws TeamNotFoundException;

    /**
     *
     * @param team
     */
    void deleteTeam(Team team);

    /**
     *
     * @param teamID
     * @throws TeamNotFoundException
     */
    void deleteTeamByID(int teamID) throws TeamNotFoundException;
}
