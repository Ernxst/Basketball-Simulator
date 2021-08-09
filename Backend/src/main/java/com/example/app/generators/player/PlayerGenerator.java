package com.example.app.generators.player;

import com.example.app.util.MathsUtil;
import com.example.app.util.Util;
import com.example.entities.player.Player;
import com.example.entities.player.util.Archetype;
import com.example.entities.player.util.PlayerConstants;
import com.example.entities.player.util.Position;
import com.example.entities.player.util.attributes.PlayerAttributes;
import com.example.entities.player.util.contract.Contract;
import com.example.services.NameService;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;


/**
 * Class to randomly generate a player.
 */
@AllArgsConstructor
public class PlayerGenerator {
    private final NameService nameService;

    /**
     * Randomly generate a player at a specific position.
     *
     * @param yearsSinceStart the number of years since the league started - this affects
     *                        the number of years remaining on a player's contract.
     * @param position        the position the player will play in.
     * @return a randomly generated player.
     */
    public Player generatePlayer(int yearsSinceStart, Position position) {
        String firstName = nameService.randomFirstName();
        String lastName = nameService.randomLastName();
        String college = nameService.randomCollege();

        double weight = position.randomWeight();
        double height = position.randomHeight();
        double wingspan = generateWingspan(height);
        Position secondaryPosition = position.randomSecondaryPosition(height);

        int age = position.randomAge();
        LocalDate birthDate = generateBirthDate(age);

        int yearsPro = generateYearsPro(age, yearsSinceStart);

        double standingVertical = position.generateStandingVertical(weight, age);
        double maxVertical = position.generateMaxVertical(standingVertical);

        int baseOverall;
        if (yearsPro < 3)
            baseOverall = OverallGenerator.generateRookieOverall(age);
        else
            baseOverall = OverallGenerator.generateOverall(age, yearsPro, yearsSinceStart);
        int potentialOverall = OverallGenerator.generatePotentialOverall(age, yearsPro, baseOverall);

        Archetype archetype = Archetype.randomArchetype(position, baseOverall);
        AttributesGenerator attributesGenerator = new AttributesGenerator(baseOverall, potentialOverall, position,
                archetype, age, weight, height, wingspan);
        PlayerAttributes playerAttributes = attributesGenerator.getPlayerAttributes();
        PlayerAttributes potentialAttributes = attributesGenerator.getPotentialAttributes();
        Contract contract = ContractGenerator.generateContract(age, yearsPro, baseOverall, potentialOverall, yearsSinceStart);

        Player player = new Player();
        player.setFirstName(firstName);
        player.setLastName(lastName);
        player.setPosition(position);
        player.setSecondaryPosition(secondaryPosition);
        player.setHeight(height);
        player.setWeight(weight);
        player.setWingspan(wingspan);
        player.setStandingVertical(standingVertical);
        player.setMaxVertical(maxVertical);
        player.setArchetype(archetype);
        player.setCollege(college);
        player.setBirthDate(birthDate);
        player.setYearsPro(yearsPro);
        player.setOverall(baseOverall);
        player.setPotentialOverall(potentialOverall);
        player.setAllContracts(new ArrayList<>());
        player.setContract(1, contract);

//        player.setPlayerAttributes(playerAttributes);
//        player.setPotentialAttributes(potentialAttributes);

        return player;
    }

    /**
     * Randomly generate a player.
     *
     * @param yearsSinceStart the number of years since the league started - this affects
     *                        the number of years remaining on a player's contract.
     * @return a randomly generated player.
     */
    public Player generatePlayer(int yearsSinceStart) {
        Position position = Position.randomPosition();
        return generatePlayer(yearsSinceStart, position);
    }

    /**
     * Generate a random birth date based on a player's age.
     *
     * @param age the age of the player.
     * @return the date of birth of the player.
     */
    protected LocalDate generateBirthDate(int age) {
        int birthYear = LocalDate.now().minusYears(age).getYear();
        LocalDate start = LocalDate.of(birthYear, 1, 1);
        LocalDate end = LocalDate.of(birthYear, 12, 31);
        return Util.randomDate(start, end);
    }

    /**
     * Generate a random wingspan according to a normal distribution.
     *
     * @param height the height of the player. On average, a player's wingspan is 10.16cm longer than their height.
     * @return a random wingspan.
     */
    protected double generateWingspan(double height) {
        return MathsUtil.randomNormalDistributionInRange(height - 1.86, height + 17.18, height + 10.16, 1.039);
    }

    /**
     * Randomly generate the number of years a player has been in the league based on their age.
     *
     * @param age             the age of the player.
     * @param yearsSinceStart the number of years since the league started
     * @return the number of years the player has been in the league.
     */
    protected int generateYearsPro(int age, int yearsSinceStart) {
        switch (age) {
            case PlayerConstants.MIN_AGE:
                return 0;
            case 20:
                return Util.randomInt(0, Math.min(1, yearsSinceStart));
            case 21:
                return Util.randomInt(0, Math.min(2, yearsSinceStart));
            case 22:
                return Util.randomInt(0, Math.min(3, yearsSinceStart));
        }
        int maxYears = Math.min(yearsSinceStart, age - PlayerConstants.MIN_AGE);
        return MathsUtil.randomNormalDistributionInRange(0, maxYears, 3 * maxYears / 4.0, 1);
    }
}
