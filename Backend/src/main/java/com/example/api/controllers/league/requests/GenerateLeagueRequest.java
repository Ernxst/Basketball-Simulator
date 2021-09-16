package com.example.api.controllers.league.requests;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GenerateLeagueRequest {
    @NotNull
    private String leagueName;
    @NotNull
    private String startDate;
    @NotNull
    private int numOfTeams;
    @NotNull
    private String teamState;
    @NotNull
    private String teamName;
}
