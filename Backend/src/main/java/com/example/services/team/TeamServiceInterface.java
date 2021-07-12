package com.example.services.team;


import com.example.entities.team.Team;

public interface TeamServiceInterface {
    int randomTeamIconID();

    int insertTeam(Team team);

    Team getTeamByID(int teamID) throws TeamNotFoundException;

    void deleteTeam(Team team);

    void deleteTeamByID(int teamID) throws TeamNotFoundException;
}
