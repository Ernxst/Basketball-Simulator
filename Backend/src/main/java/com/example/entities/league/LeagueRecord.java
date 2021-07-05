package com.example.entities.league;

import java.time.LocalDate;

public class LeagueRecord {

    private int playerID;
    private int value;
    private int season;
    private LocalDate dateSet;
    public LeagueRecord(int playerID, int value, int season, LocalDate dateSet) {
        this.playerID = playerID;
        this.value = value;
        this.season = season;
        this.dateSet = dateSet;
    }

    public LeagueRecord() {
        playerID = -1;
        value = -1;
        season = -1;
        dateSet = null;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public LocalDate getDateSet() {
        return dateSet;
    }

    public void setDateSet(LocalDate dateSet) {
        this.dateSet = dateSet;
    }

    @Override
    public String toString() {
        return "{" +
                "\n    Player ID: " + playerID +
                "\n    Value    : " + value +
                "\n    Season   : " + season +
                "\n    Date Set : " + dateSet +
                '}';
    }

    public enum Record {
        MOST_PTS("Most Points"), MOST_REB("Most Rebounds"),
        MOST_AST("Most Assists"), MOST_STL("Most Steals"),
        MOST_BLK("Most Blocks"), MOST_TO("Most Turnovers"),
        MOST_FTA("Most Free Throws Attempted"), MOST_FTM("Most Free Throws Made"),
        MOST_FGA("Most Field Goals Attempted"), MOST_FGM("Most Field Goals Made"),
        MOST_3PA("Most Three Pointers Attempted"), MOST_3PM("Most Three Pointers Made");

        public static final Record[] records = values();
        private final String label;

        Record(String label) {
            this.label = label;
        }

        String getLabel() {
            return label;
        }
    }
}
