package com.example.services.league;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.app.generators.league.LeagueGenerator;
import com.example.entities.league.League;
import com.example.entities.league.LeagueStandings;
import com.example.entities.team.Team;
import com.example.entities.user.User;
import com.example.repositories.LeagueRepository;
import com.example.services.NameService;
import com.example.services.freeAgent.FreeAgentService;
import com.example.services.league.season.LeagueSeasonService;
import com.example.services.player.PlayerService;
import com.example.services.team.TeamService;
import com.example.services.user.UserService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LeagueService implements LeagueServiceInterface {
    private final LeagueRepository leagueRepository;
    private final FreeAgentService freeAgentService;
    private final TeamService teamService;
    private final NameService nameService;
    private final PlayerService playerService;
    private final UserService userService;
    private final LeagueSeasonService leagueSeasonService;

    @Override
    public League newLeague(String username, String name, LocalDate startDate, int numOfTeams, String teamName,
            String state) {
        LeagueGenerator leagueGenerator = new LeagueGenerator(this, freeAgentService, teamService, nameService,
                playerService, leagueSeasonService);
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
    public List<LeagueSave> getLeagueSaves(String username) throws UsernameNotFoundException {
        User user = (User) userService.loadUserByUsername(username);
        Map<Integer, League> leagues = user.getLeagues();
        List<LeagueSave> saves = new ArrayList<>();
        for (League league : leagues.values()) {
            LeagueSave save = getSave(league);
            saves.add(save);
        }
        return saves;
    }

    private LeagueSave getSave(League league) {
        Team userTeam = league.getUserTeam();
        int leagueID = league.getLeagueID();
        String leagueName = league.getName();
        String teamState = userTeam.getState();
        String teamName = userTeam.getName();
        int iconID = userTeam.getIconID();
        LocalDate currentDate = league.getCurrentDate();
        int currentSeason = league.getCurrentSeason();
        LocalDate lastPlayed = league.getLastPlayed();
        Map<Integer, LeagueStandings> allStandings = userTeam.getAllStandings();
        LeagueStandings standings = allStandings.get(currentSeason);
        int wins = standings.getWins();
        int losses = standings.getLosses();
        return new LeagueSaveImpl(leagueID, leagueName, teamState, teamName, iconID, currentDate, currentSeason, lastPlayed, wins,
                losses);
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
