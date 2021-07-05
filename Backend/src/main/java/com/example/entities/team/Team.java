package com.example.entities.team;

import com.example.entities.player.Player;

import java.time.LocalDate;
import java.util.Map;

//@Entity
public class Team {
    private final int relocations;
    private final int renames;
    private final boolean isUserTeam;
    private final LocalDate dateFounded;
    private String state;
    private String name;
    //    @ElementCollection
    private Map<Integer, Player> players;

    //    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private int teamID;
    private int leagueID;
    private int iconID;

    public Team(String state, String name, int relocations, int renames, boolean isUserTeam,
                LocalDate dateFounded, int iconID) {
        this.state = state;
        this.name = name;
        this.relocations = relocations;
        this.renames = renames;
        this.isUserTeam = isUserTeam;
        this.dateFounded = dateFounded;
        this.iconID = iconID;
    }

    public Team(String state, String name, int relocations, int renames, boolean isUserTeam, LocalDate dateFounded,
                int iconID, Map<Integer, Player> players) {
        this(state, name, relocations, renames, isUserTeam, dateFounded, iconID);
        this.players = players;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return getState() + " " + getName();
    }

    public int getRelocations() {
        return relocations;
    }

    public int getRenames() {
        return renames;
    }

    public boolean isUserTeam() {
        return isUserTeam;
    }

    public double getCapSpace() {
        return TeamConstants.MAX_CAP - getTotalCap();
    }

    public double getTotalCap() {
        double totalCap = 0;
        for (Player player : players.values()) {
            totalCap += player.getContract().getSalary();
        }
        return totalCap;
    }

    public LocalDate getDateFounded() {
        return dateFounded;
    }

    public Map<Integer, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<Integer, Player> players) {
        this.players = players;
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public int getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(int leagueID) {
        this.leagueID = leagueID;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public Player getPlayerById(int playerId) {
        return players.getOrDefault(playerId, null);
    }

    public Team copy() {
        return new Team(state, name, relocations, renames, isUserTeam, dateFounded, iconID, players);
    }
}
