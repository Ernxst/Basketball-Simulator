package com.example.api.controllers.league.responses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.example.api.util.AbstractResponse;
import com.example.entities.league.LeagueConstants;
import com.example.services.league.LeagueSave;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LeagueSavesResponse extends AbstractResponse {
    private final List<LeagueSave> saves;

    public LeagueSavesResponse(List<LeagueSave> leagueSaves) {
        saves = new ArrayList<>(Collections.nCopies(LeagueConstants.MAX_LEAGUES, new EmptyLeagueSave<>()));
        for (int i = 0; i < leagueSaves.size(); i++) {
            LeagueSave leagueSave = leagueSaves.get(i);
            if (leagueSave != null)
                saves.set(i, leagueSave);
        }
    }

    @NoArgsConstructor
    private class EmptyLeagueSave<K, V> extends HashMap<K, V> implements LeagueSave {

    }
}
