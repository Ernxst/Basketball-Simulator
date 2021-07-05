package com.example.app.generators;

import com.example.app.generators.player.PlayerGenerator;
import com.example.entities.player.Player;
import com.example.entities.player.util.PlayerConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerGeneratorTest {
    @Test
    public void generatePlayer() {
        Player player = PlayerGenerator.generatePlayer(20);
        assertTrue(player.getYearsPro() <= 20);
        assertTrue(player.getOverall() <= PlayerConstants.MAX_OVERALL);
        assertTrue(player.getPotentialOverall() <= PlayerConstants.MAX_OVERALL);
        assertTrue(player.getPotentialOverall() >= player.getOverall());
    }

    @Test
    public void generatePlayerWithZeroYearsSinceStartOfLeague() {
        Player player = PlayerGenerator.generatePlayer(0);
        assertEquals(player.getYearsPro(), 0);
    }
}
