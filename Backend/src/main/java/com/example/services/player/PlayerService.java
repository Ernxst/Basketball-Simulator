package com.example.services.player;

import com.example.entities.player.Player;
import com.example.repositories.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlayerService implements PlayerServiceInterface {
    private final PlayerRepository playerRepository;

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
        return playerRepository.findPlayersByPlayerIDIn((List<Integer>) playerIDs);
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
