package com.example.services.player;

import com.example.entities.player.Player;

import java.util.List;

public interface PlayerServiceInterface {
    Player findPlayerByPlayerID(int playerID) throws PlayerNotFoundException;

    List<Player> findPlayersByPlayerIDsIn(Iterable<Integer> playerIDs);

    List<Player> findAllPlayersInTeam(int teamID);

    void deletePlayer(Player player);

    int insertPlayer(Player player);
}
