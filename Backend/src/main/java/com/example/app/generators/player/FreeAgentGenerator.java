package com.example.app.generators.player;

import com.example.app.util.Util;
import com.example.entities.player.FreeAgent;
import com.example.entities.player.Player;
import com.example.entities.player.util.Archetype;
import com.example.entities.player.util.Position;
import com.example.entities.player.util.attributes.PlayerAttributes;
import com.example.services.NameService;

public class FreeAgentGenerator extends PlayerGenerator {
    public FreeAgentGenerator(NameService nameService) {
        super(nameService);
    }

    /**
     * Randomly generate a free agent.
     *
     * @param yearsSinceStart the number of years since the league started - this affects
     *                        the number of years remaining on a player's contract.
     * @return a randomly generated free agent.
     */
    public FreeAgent generateFreeAgent(int yearsSinceStart) {
        Player player = generatePlayer(yearsSinceStart);
        int age = player.getAge();
        int yearsPro = Util.randomInt(0, player.getYearsPro());
        Position position = player.getPosition();
        double weight = player.getWeight();
        double height = player.getHeight();
        double wingspan = player.getWingspan();

        int baseOverall = OverallGenerator.generateFreeAgentOverall(age, yearsPro, yearsSinceStart);
        int potentialOverall = OverallGenerator.generatePotentialOverall(age, yearsPro, baseOverall);
        Archetype archetype = player.getArchetype();
        AttributesGenerator attributesGenerator = new AttributesGenerator(baseOverall, potentialOverall, position,
                archetype, age, weight, height, wingspan);
        PlayerAttributes playerAttributes = attributesGenerator.getPlayerAttributes();
        PlayerAttributes potentialAttributes = attributesGenerator.getPotentialAttributes();

        player.setOverall(baseOverall);
//        player.setPlayerAttributes(playerAttributes);
        player.setPotentialOverall(potentialOverall);
//        player.setPotentialAttributes(potentialAttributes);
//        player.setContract(null);
        return player.toFreeAgent();
    }
}
