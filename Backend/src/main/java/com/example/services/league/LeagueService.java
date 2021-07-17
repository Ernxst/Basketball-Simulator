package com.example.services.league;

import com.example.app.generators.league.LeagueGenerator;
import com.example.entities.league.League;
import com.example.entities.user.User;
import com.example.repositories.LeagueRepository;
import com.example.services.NameService;
import com.example.services.freeAgent.FreeAgentService;
import com.example.services.league.season.LeagueSeasonService;
import com.example.services.player.PlayerService;
import com.example.services.team.TeamService;
import com.example.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class LeagueService implements LeagueServiceInterface {
    @Autowired
    private LeagueRepository leagueRepository;
    @Autowired
    private FreeAgentService freeAgentService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private NameService nameService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private UserService userService;
    @Autowired
    private LeagueSeasonService leagueSeasonService;

    @Override
    public League newLeague(String username, String name, LocalDate startDate,
                            int numOfTeams, String teamName, String state) {
        LeagueGenerator leagueGenerator = new LeagueGenerator(this, freeAgentService,
                teamService, nameService, playerService, leagueSeasonService);
        UserDetails userDetails = userService.loadUserByUsername(username);
        User user = new User(username, userDetails.getPassword());
        League league = leagueGenerator.generateLeague(user, name, startDate, numOfTeams, teamName, state);
        insertLeague(league);
        return league;
    }

    @Override
    public int insertLeague(League league) {
        league = leagueRepository.save(league);
        return league.getLeagueID();
    }

    @Override
    public League getLeagueByID(int leagueID) throws LeagueNotFoundException {
        Optional<League> optionalLeague = leagueRepository.findById(leagueID);
        return optionalLeague.orElseThrow(() -> new LeagueNotFoundException("Could not find the given league"));
    }

    @Override
    public void deleteLeague(League league) {
        leagueRepository.delete(league);
    }

    @Override
    public void deleteLeagueByID(int leagueID) throws LeagueNotFoundException {
        League league = getLeagueByID(leagueID);
        deleteLeague(league);
    }
}
