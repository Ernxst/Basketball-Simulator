package com.example.app.generators;

import com.example.app.generators.player.PlayerGenerator;
import com.example.entities.player.Player;
import com.example.entities.player.util.PlayerConstants;
import com.example.services.NameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerGeneratorTest {
    @Autowired
    private NameService nameService;
    private final PlayerGenerator playerGenerator = new PlayerGenerator(nameService);

    @Test
    public void generatePlayer() {
        Player player = playerGenerator.generatePlayer(20);
        assertTrue(player.getYearsPro() <= 20);
        assertTrue(player.getOverall() <= PlayerConstants.MAX_OVERALL);
        assertTrue(player.getPotentialOverall() <= PlayerConstants.MAX_OVERALL);
        assertTrue(player.getPotentialOverall() >= player.getOverall());
    }

    @Test
    public void generatePlayerWithZeroYearsSinceStartOfLeague() {
        Player player = playerGenerator.generatePlayer(0);
        assertEquals(player.getYearsPro(), 0);
    }
}
