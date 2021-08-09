package com.example.services.player;

import com.example.entities.player.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface PlayerServiceInterface {
    /**
     *
     * @param playerID
     * @return
     * @throws PlayerNotFoundException
     */
    Player findPlayerByPlayerID(int playerID) throws PlayerNotFoundException;

    List<Player> findPlayersByPlayerIDsIn(Iterable<Integer> playerIDs);

    /**
     *
     * @param teamID
     * @return
     */
    List<Player> findAllPlayersInTeam(int teamID);

    /**
     *
     * @param player
     */
    void deletePlayer(Player player);

    /**
     *
     * @param player
     * @return
     */
    int insertPlayer(Player player);

    /**
     *
     * @param players
     * @return
     */
    Iterable<Player> insertPlayers(Player[] players);
}
