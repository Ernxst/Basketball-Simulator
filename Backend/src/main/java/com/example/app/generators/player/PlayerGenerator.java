package com.example.app.generators.player;

import com.example.app.util.MathsUtil;
import com.example.app.util.Util;
import com.example.db.interfaces.NameGenerator;
import com.example.entities.player.Player;
import com.example.entities.player.util.Archetype;
import com.example.entities.player.util.PlayerConstants;
import com.example.entities.player.util.Position;
import com.example.entities.player.util.attributes.PlayerAttributes;
import com.example.entities.player.util.contract.Contract;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Class to randomly generate a player.
 */
public class PlayerGenerator {
    /**
     * Randomly generate a player.
     *
     * @param yearsSinceStart the number of years since the league started - this affects
     *                        the number of years remaining on a player's contract.
     * @return a randomly generated player.
     */
    public static Player generatePlayer(int yearsSinceStart) {
        String firstName = NameGenerator.randomFirstName();
        String lastName = NameGenerator.randomLastName();
        String college = NameGenerator.randomCollege();
        Position position = Position.randomPosition();

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

        return new Player(firstName, lastName, position, secondaryPosition, height, weight, wingspan, standingVertical,
                maxVertical, archetype, college, birthDate, yearsPro, baseOverall, potentialOverall, contract, playerAttributes, potentialAttributes);
    }

    /**
     * Generate a random birth date based on a player's age.
     *
     * @param age the age of the player.
     * @return the date of birth of the player.
     */
    protected static LocalDate generateBirthDate(int age) {
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
    protected static double generateWingspan(double height) {
        return MathsUtil.randomNormalDistributionInRange(height - 1.86, height + 17.18, height + 10.16, 1.039);
    }

    /**
     * Randomly generate the number of years a player has been in the league based on their age.
     *
     * @param age             the age of the player.
     * @param yearsSinceStart the number of years since the league started
     * @return the number of years the player has been in the league.
     */
    protected static int generateYearsPro(int age, int yearsSinceStart) {
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

    public static double test(int numOfPlayers, Player[] players) {
        long duration = 0;
        for (int i = 0; i < numOfPlayers; i++) {
            long startTime = System.nanoTime();
            Player player = generatePlayer(20);
            long endTime = System.nanoTime();
            duration += (endTime - startTime);
            players[i] = player;
//            FreeAgent player = FreeAgentGenerator.generateFreeAgent(20);
        }
        duration /= 1000000;
        return duration;
    }

    public static void summarise(Player[] players, double duration) {
        int numOfPlayers = players.length;
        List<Integer> overalls = new ArrayList<>();
        double sumAge = 0;
        double sumHeight = 0;
        double sumWeight = 0;
        double sumWingspan = 0;
        double sumStandVert = 0;
        double sumVert = 0;
        int sumOverall = 0;
        int sumPotential = 0;
        double sumSalary = 0;
        int rookies = 0;

        for (Player player : players) {
            sumAge += player.getAge();
            sumHeight += player.getHeight();
            sumWeight += player.getWeight();
            sumWingspan += player.getWingspan();
            sumStandVert += player.getStandingVertical();
            sumVert += player.getMaxVertical();
            sumOverall += player.getOverall();
            sumPotential += player.getPotentialOverall();
            sumSalary += player.getContract().getSalary();
            overalls.add(player.getOverall());
            if (player.getYearsPro() == 0)
                rookies++;
        }
        output(numOfPlayers, duration, overalls, sumOverall, sumPotential, sumHeight, sumWeight,
                sumWingspan, sumStandVert, sumVert, sumAge, sumSalary, rookies);
    }

    public static void output(int numOfPlayers, double duration, List<Integer> overalls, int sumOverall,
                              int sumPotential, double sumHeight, double sumWeight, double sumWingspan,
                              double sumStandVert, double sumVert, double sumAge, double sumSalary, int rookies) {
        double avgHeight = sumHeight / numOfPlayers;
        double avgWingspan = sumWingspan / numOfPlayers;
        int[] heightFeetInches = MathsUtil.cmToFeetAndInches(avgHeight);
        int[] wingspanFeetInches = MathsUtil.cmToFeetAndInches(avgWingspan);

        Map<Integer, Integer> frequencies = Util.countFrequencies(overalls);

        System.out.println();
        System.out.println("----------------------------------------------------------");
        System.out.println("                         SUMMARY");
        System.out.println("----------------------------------------------------------");
        System.out.println("Number of players generated       : " + numOfPlayers);
        System.out.println("Time taken                        : " + duration + "ms");
        System.out.println("Average Age                       : " + sumAge / numOfPlayers);
        System.out.println("Average Height (cm)               : " + avgHeight);
        System.out.println("Average Height (ft, in)           : " + heightFeetInches[0] + "\"" + heightFeetInches[1]);
        System.out.println("Average Weight (lbs)              : " + sumWeight / numOfPlayers);
        System.out.println("Average Wingspan (cm)             : " + avgWingspan);
        System.out.println("Average Wingspan (ft, in)         : " + wingspanFeetInches[0] + "\"" + wingspanFeetInches[1]);
        System.out.println("Average Standing Vertical (inches): " + sumStandVert / numOfPlayers);
        System.out.println("Average Max Vertical (inches)     : " + sumVert / numOfPlayers);
        System.out.printf("Average Salary ($)                : %.0f\n", sumSalary / numOfPlayers);
        System.out.println("Average Potential                 : " + sumPotential / numOfPlayers);
        System.out.println("Average Overall                   : " + sumOverall / numOfPlayers);
        System.out.println("Highest Overall                   : " + Collections.max(overalls));
        System.out.println("Lowest Overall                    : " + Collections.min(overalls));
        overalls.sort(Collections.reverseOrder());
        System.out.println("Overalls                          : " + overalls);
        System.out.println("Frequencies                       : " + frequencies);
        System.out.println("Rookies:                          : " + rookies);
        System.out.println();
    }

    public static void generate(int trials) {
        int numOfPlayers = 544;
        for (int i = 0; i < trials; i++) {
            Player[] players = new Player[numOfPlayers];
            double duration = test(numOfPlayers, players);
            summarise(players, duration);
        }
    }

    public static void main(String[] args) {
        generate(5);
    }
}
