package com.example.services.team;

import com.example.entities.team.Team;
import com.example.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService implements TeamServiceInterface {
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public int randomTeamIconID() {
        return 1; // TODO - Implement once icons are in DB
    }

    @Override
    public int insertTeam(Team team) {
        team = teamRepository.save(team);
        return team.getTeamID();
    }

    @Override
    public Team getTeamByID(int teamID) throws TeamNotFoundException {
        Optional<Team> optionalTeam = teamRepository.findById(teamID);
        return optionalTeam.orElseThrow(() -> new TeamNotFoundException("Could not find the given team"));
    }

    @Override
    public void deleteTeam(Team team) {
        teamRepository.delete(team);
    }

    @Override
    public void deleteTeamByID(int teamID) throws TeamNotFoundException {
        Team team = getTeamByID(teamID);
        deleteTeam(team);
    }
}
