package com.example.api.controllers.constants.responses;

import com.example.api.util.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class TeamStatesResponse extends AbstractResponse {
    private final List<String> teamStates;
}
