package com.example.repositories;

import com.example.entities.player.Player;

import java.util.List;
import java.util.Optional;

//@Repository
//public interface PlayerRepository extends CrudRepository<Player, Integer> {
public interface PlayerRepository {
    Optional<Player> findPlayerByPlayerID(int playerID);

    List<Player> findPlayersByPlayerID(List<Integer> playerIDs);
}
