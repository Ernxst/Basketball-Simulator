package com.example.app.generators.player;

import com.example.app.util.MathsUtil;
import com.example.app.util.Util;
import com.example.entities.player.util.PlayerConstants;

import java.util.HashMap;
import java.util.Map;


public class OverallGenerator {
    private static final int averageOverall = 75;
    private static final double stdDeviation = 8.4;
    private static final int rookieAvgOverall = 65;
    private static final double rookieStdDeviation = 6.4;
    private static final int freeAgentAvgOverall = 66;
    private static final double freeAgentStdDeviation = 5;
    private static final int meanAgeForHighestOverall = 29;
    private static final int freeAgentMeanAgeForHighestOverall = 27;
    private static final int rookieMeanAgeForHighestOverall = 25;
    private static final double overallStdDeviation = 4;
    private static final int potentialMultiplier = 3000;

    /**
     * Generate an overall rating for a rookie.
     *
     * @param age the age of the player, players in their prime years (26-32) are more likely
     *            to have higher overalls.
     * @return a random overall for a rookie overall according to a normal distribution
     */
    public static int generateRookieOverall(int age) {
        int ageBasedOverall = MathsUtil.gaussianInt(PlayerConstants.MAX_OVERALL, age, rookieMeanAgeForHighestOverall, overallStdDeviation);
        int normallyDistributedOverall = MathsUtil.randomNormalDistributionInRange(PlayerConstants.MIN_ROOKIE_OVERALL, PlayerConstants.MAX_ROOKIE_OVERALL, rookieAvgOverall, rookieStdDeviation);
        return (ageBasedOverall + normallyDistributedOverall) / 2;
//        return Math.max(averageOverall, PlayerConstants.MIN_OVERALL_RATING);
    }

    /**
     * Generate an overall rating for a free agent.
     *
     * @param age      the age of the player, players in their prime years (26-32) are more likely
     *                 to have higher overalls.
     * @param yearsPro the number of years the player has been in the league, more experienced players are more
     *                 likely to have higher ratings.
     * @return a random overall for a free agent rating according to a normal distribution
     */
    public static int generateFreeAgentOverall(int age, int yearsPro, int yearsSinceStart) {
        return generateOverall(age, yearsPro, yearsSinceStart, PlayerConstants.MAX_FREE_AGENT_OVERALL, freeAgentMeanAgeForHighestOverall,
                freeAgentAvgOverall, freeAgentAvgOverall, freeAgentStdDeviation);
    }

    /**
     * Generate an overall rating for a player.
     *
     * @param age             the age of the player, players in their prime years (26-32) are more likely
     *                        to have higher overalls.
     * @param yearsPro        the number of years the player has been in the league, more experienced players are more
     *                        likely to have higher overalls.
     * @param yearsSinceStart the number of years since the league started.
     * @return a random overall player rating according to a normal distribution.
     */
    public static int generateOverall(int age, int yearsPro, int yearsSinceStart) {
        return generateOverall(age, yearsPro, yearsSinceStart, PlayerConstants.MAX_OVERALL, meanAgeForHighestOverall,
                overallStdDeviation, averageOverall, stdDeviation);
    }

    private static int generateOverall(int age, int yearsPro, int yearsSinceStart, int maxOverall,
                                       int meanAgeForHighestOverall, double overallStdDeviation,
                                       int averageOverall, double stdDeviation) {
        int maxYearsPro = Math.min(yearsSinceStart, PlayerConstants.MAX_AGE - PlayerConstants.MIN_AGE);
        double multiplier = maxOverall / (maxYearsPro + 1.0);
        double experienceBasedOverall = yearsPro * multiplier;
        int ageBasedOverall = MathsUtil.gaussianInt(PlayerConstants.MAX_OVERALL, age, meanAgeForHighestOverall, overallStdDeviation);
        int normallyDistributedOverall = MathsUtil.randomNormalDistributionInRange(PlayerConstants.MIN_OVERALL, maxOverall, averageOverall, stdDeviation);
        Map<Double, Double> distribution = new HashMap<>();
        distribution.put(experienceBasedOverall, 1.0);
        distribution.put((double) ageBasedOverall, 24.0);
        distribution.put((double) normallyDistributedOverall, 75.0);
        return (int) MathsUtil.weightedAverage(distribution);
    }

    /**
     * Generate a player's potential overall rating.
     *
     * @param age      the age of the player, younger players have higher potentials, relative to their
     *                 current overall; players in their prime (26-32) can either stay at the same rating or
     *                 increase by 1; players over 32 are in the tail-end of their career and their rating is
     *                 unlikely to increase.
     * @param yearsPro the number of years the player has been in the league, the more experience, the lower
     *                 the potential.
     * @param overall  the overall of the player, the maximum rating for a rookie is 85; rookies with these
     *                 ratings have the potential to become the best players in the league.
     * @return the potential overall of the player.
     */
    public static int generatePotentialOverall(int age, int yearsPro, int overall) {
        int potential;
        if (overall >= 83 && age < 22) {
            potential = Util.randomInt(94, PlayerConstants.MAX_OVERALL);
        } else if (26 <= age && age < 28) {
            potential = Util.randomInt(overall, overall + 3);
        } else if (28 <= age && age <= 32) {
            potential = Util.randomInt(overall, overall + 1);
        } else if (age > 32) {
            potential = overall;
        } else {
            yearsPro++; // Ensures yearsPro is not zero.
            potential = potentialMultiplier / (age * yearsPro);
        }
        if (potential < overall)
            potential = overall + Math.abs(overall - potential);
        return Math.min(potential, PlayerConstants.MAX_OVERALL);
    }
}
