package com.example.services.player;

import com.example.entities.player.Player;
import com.example.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService implements PlayerServiceInterface {
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public int insertPlayer(Player player) {
        player = playerRepository.save(player);
        return player.getPlayerID();
    }

    @Override
    public Player findPlayerByPlayerID(int playerID) throws PlayerNotFoundException {
        Optional<Player> optionalPlayer = playerRepository.findPlayerByPlayerID(playerID);
        return optionalPlayer.orElseThrow(() -> new PlayerNotFoundException("Could not find player"));
    }

    @Override
    public List<Player> findPlayersByPlayerIDsIn(Iterable<Integer> playerIDs) {
        return playerRepository.findPlayersByPlayerIDsIn((List<Integer>) playerIDs);
    }

    @Override
    public List<Player> findAllPlayersInTeam(int teamID) {
        return null;
    }

    @Override
    public void deletePlayer(Player player) {
        playerRepository.delete(player);
    }

    @Override
    public Iterable<Player> insertPlayers(Player[] players) {
        Iterable<Player> playersToSave = Arrays.asList(players);
         return playerRepository.saveAll(playersToSave);
    }
}
