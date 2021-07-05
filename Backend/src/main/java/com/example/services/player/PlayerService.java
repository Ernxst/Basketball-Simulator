package com.example.services.player;

import com.example.entities.player.Player;
import com.example.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service
public class PlayerService implements PlayerServiceInterface {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Optional<Player> findPlayerByPlayerID(int playerID) {
        return playerRepository.findPlayerByPlayerID(playerID);
    }

    @Override
    public List<Player> findPlayersByPlayerIDs(List<Integer> playerIDs) {
        return playerRepository.findPlayersByPlayerID(playerIDs);
    }

    @Override
    public List<Player> findAllPlayersInTeam(int teamID) {
        return null;
    }

    @Override
    public void deletePlayer(Player player) {
//        playerRepository.delete(player);
    }
}
