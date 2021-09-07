package com.example.api.controllers.league.requests;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class GenerateLeagueRequest {
    private String username;
    private String leagueName;
    private String startDate;
    private int numOfTeams;
    private String teamState;
    private String teamName;
}
