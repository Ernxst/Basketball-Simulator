package com.example.entities.player.util;

import com.example.app.util.MathsUtil;
import com.example.app.util.Util;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 https://www.stack.com/a/nba-prospects-jump-higher
 https://www.topendsports.com/sport/basketball/testing-draft-results-2015.htm
 http://www.draftexpress.com/average-measurements-by-position/2017/NBA+Draft+Combine/all/60/
*/
public enum Position {
    /*
     * Data from https://www.thehoopsgeek.com/average-nba-height/
     */
    POINT_GUARD("PG", "Point Guard", 27.66, 4.67,
            175, 200.66, 190, 9.19, 183,
            202, 193, 12.58, 22, 38,
            30, 0, 5, 10),

    SHOOTING_GUARD("SG", "Shooting Guard", 26.276, 3.583,
            188, 203.2, 194.056, 9.19, 195, 215,
            202, 12.58, 24, 37, 28,
            0, 4, 10),

    SMALL_FORWARD("SF", "Small Forward", 26.376, 3.586,
            195.58, 213.36, 199.136, 9.19, 205, 260,
            213, 12.58, 26, 38, 30,
            0, 4, 9),

    POWER_FORWARD("PF", "Power Forward", 26.817, 4.375,
            198.12, 218.44, 203.708, 9.19, 215, 280,
            231, 12.58, 22, 33, 28,
            0, 3, 8),

    CENTER("C", "Center", 27.0666, 4.03,
            203.2, 228.6, 209.804, 9.19, 225, 295,
            248, 12.58, 21, 31, 27,
            0, 3, 8);

    public static final Position[] positions = values();
    public static final int numOfPositions = positions.length;
    private static final Map<String, Position> positionsMap = Map.of(
            POINT_GUARD.getShortName(), POINT_GUARD,
            SHOOTING_GUARD.getShortName(), SHOOTING_GUARD,
            SMALL_FORWARD.getShortName(), SMALL_FORWARD,
            POWER_FORWARD.getShortName(), POWER_FORWARD,
            CENTER.getShortName(), CENTER
    );

    private static final List<Pair<Position, Double>> weights = new ArrayList<>() {{
        add(new Pair<>(POINT_GUARD, 0.17222));
        add(new Pair<>(SHOOTING_GUARD, 0.2611111));
        add(new Pair<>(SMALL_FORWARD, 0.18148));
        add(new Pair<>(POWER_FORWARD, 0.22407));
        add(new Pair<>(CENTER, 0.161111));
    }};
    private static final EnumeratedDistribution<Position> distribution = new EnumeratedDistribution<>(weights);
    private static final double POSITION_BOUNDARY_MULTIPLIER = 1.01;

    private final String shortName;
    private final String fullName;
    private final double averageAge;
    private final double ageStdDeviation;

    private final double minHeight;
    private final double maxHeight;
    private final double averageHeight;
    private final double heightStdDeviation;

    private final double minWeight;
    private final double maxWeight;
    private final double averageWeight;
    private final double weightStdDeviation;

    private final double minVertical;
    private final double maxVertical;
    private final double avgVertical;
    private final double verticalStdDeviation;
    private final int lowerVerticalBoundary;
    private final int upperVerticalBoundary;

    private final double lowerHeightBoundary;
    private final double upperHeightBoundary;

    Position(String shortName, String fullName, double averageAge, double ageStdDeviation,
             double minHeight, double maxHeight, double averageHeight, double heightStdDeviation, double minWeight,
             double maxWeight, double averageWeight, double weightStdDeviation, double minVertical, double maxVertical,
             double avgVertical, double verticalStdDeviation, int lowerVerticalBoundary, int upperVerticalBoundary) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.averageAge = averageAge;
        this.ageStdDeviation = ageStdDeviation;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.averageHeight = averageHeight;
        this.heightStdDeviation = heightStdDeviation;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.averageWeight = averageWeight;
        this.weightStdDeviation = weightStdDeviation;
        this.lowerHeightBoundary = averageHeight * (2 - POSITION_BOUNDARY_MULTIPLIER);
        this.upperHeightBoundary = averageHeight * POSITION_BOUNDARY_MULTIPLIER;
        this.minVertical = minVertical;
        this.maxVertical = maxVertical;
        this.avgVertical = avgVertical;
        this.verticalStdDeviation = verticalStdDeviation;
        this.lowerVerticalBoundary = lowerVerticalBoundary;
        this.upperVerticalBoundary = upperVerticalBoundary;
    }

    /**
     * Generate a random position, according to a probability distribution.
     *
     * @return a random player position.
     */
    public static Position randomPosition() {
        return distribution.sample();
    }

    /**
     * Generate a random sample of positions.
     *
     * @param sampleSize the number of positions to generate.
     * @return a random sample of positions.
     */
    public static Position[] randomPositions(int sampleSize) {
        Position[] positions = new Position[sampleSize];
        return distribution.sample(sampleSize, positions);
    }

    /**
     * Returns the position if the player's height is within the boundary for that position.
     *
     * @param height   the height of the player.
     * @param position the position to check.
     * @param lower    whether the average position height is higher or lower than the height of the player.
     * @return the position of the player.
     */
    static Position randomPositionByHeight(double height, Position position, boolean lower) {
        if ((!lower && height > position.getUpperHeightBoundary()) ||
                (lower && height < position.getLowerHeightBoundary())) {
            if (Math.random() < 0.67)
                return position;
        }
        return null;
    }

    public static Position getPositionByName(String name) {
        return positionsMap.getOrDefault(name, null);
    }

    public double getLowerHeightBoundary() {
        return lowerHeightBoundary;
    }

    public double getUpperHeightBoundary() {
        return upperHeightBoundary;
    }

    /**
     * Generate a random age based on the average for the given position, according to a normal distribution.
     *
     * @return a random age.
     */
    public int randomAge() {
        return MathsUtil.randomNormalDistributionInRange(PlayerConstants.MIN_AGE, PlayerConstants.MAX_AGE, averageAge, ageStdDeviation);
    }

    /**
     * Generate a random height based on the average for the given position, according to a normal distribution.
     *
     * @return a random height.
     */
    public double randomHeight() {
        return MathsUtil.randomNormalDistributionInRange(minHeight, maxHeight, averageHeight, heightStdDeviation);
    }

    /**
     * Generate a random weight based on the average for the given position, according to a normal distribution.
     *
     * @return a random weight.
     */
    public double randomWeight() {
        return MathsUtil.randomNormalDistributionInRange(minWeight, maxWeight, averageWeight, weightStdDeviation);
    }

    /**
     * Generate a player's standing vertical based on their weight and position and age.
     *
     * @param weight the weight of the player, heavier players are likely to have lower standing vertical leaps.
     * @param age    the age of the player - older players are likely to have lower standing vertical leaps.
     * @return the standing vertical leap of the player.
     */
    public double generateStandingVertical(double weight, int age) {
        double baseVertical = MathsUtil.randomNormalDistributionInRange(minVertical, maxVertical, avgVertical, verticalStdDeviation);
        if (weight > 265) {
            baseVertical -= Util.randomInt(0, 4);
        }
        if (age > 32) {
            baseVertical -= Util.randomInt(0, 6);
        }
        return baseVertical;
    }

    /**
     * Generate a player's maximum vertical leap based on the position and standing vertical
     *
     * @param standingVertical the standing vertical of the player directly impacts their maximum vertical.
     * @return the maximum vertical leap of the player.
     */
    public double generateMaxVertical(double standingVertical) {
        return standingVertical + Util.randomDouble(lowerVerticalBoundary, upperVerticalBoundary);
    }

    /**
     * Generate a secondary position for the given  player.
     *
     * @param height the height of the player.
     * @return a secondary position for the player.
     */
    public Position randomSecondaryPosition(double height) {
        switch (this) {
            case POINT_GUARD:
                return randomPositionByHeight(height, SHOOTING_GUARD, false);
            case SHOOTING_GUARD:
                return randomPositionByHeight(height, POINT_GUARD, SMALL_FORWARD);
            case SMALL_FORWARD:
                return randomPositionByHeight(height, SHOOTING_GUARD, POWER_FORWARD);
            case POWER_FORWARD:
                return randomPositionByHeight(height, SMALL_FORWARD, CENTER);
            case CENTER:
                return randomPositionByHeight(height, POWER_FORWARD, true);
        }
        return null;
    }

    /**
     * Returns the position if the player's height is within the boundary for that position.
     *
     * @param height        the height of the player.
     * @param lowerPosition the lower position to check.
     * @param upperPosition the upper position to check.
     * @return the position of the player.
     */
    public Position randomPositionByHeight(double height, Position lowerPosition, Position upperPosition) {
        if (height > upperHeightBoundary) {
            return randomPositionByHeight(height, upperPosition, false);
        } else if (height < lowerHeightBoundary) {
            return randomPositionByHeight(height, lowerPosition, true);
        }
        return null;
    }

    @Override
    public String toString() {
        return getFullName();
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public boolean equals(Position otherPosition) {
        return this.shortName.equals(otherPosition.getShortName());
    }
}
