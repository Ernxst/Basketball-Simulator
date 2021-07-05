package com.example.services.player;

import com.example.entities.player.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerServiceInterface {
    Optional<Player> findPlayerByPlayerID(int playerID);

    List<Player> findPlayersByPlayerIDs(List<Integer> playerIDs);

    List<Player> findAllPlayersInTeam(int teamID);

    void deletePlayer(Player player);
}
