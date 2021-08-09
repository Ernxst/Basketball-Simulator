package com.example.repositories;

import com.example.entities.player.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {
    Optional<Player> findPlayerByPlayerID(int playerID);

    List<Player> findPlayersByPlayerIDIn(List<Integer> playerIDs);
}
