package com.example.entities.player.util;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

import java.util.*;

/*
  TODO - Archetype Descriptions for: Generational, Paint Beast, Shooter, Big Guard
 */

/**
 * An archetype represents the player's play style and hence, their attributes.
 */
public enum Archetype {
    GENERATIONAL("Generational", 98, .09, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD, Position.SMALL_FORWARD, Position.POWER_FORWARD, Position.CENTER}),
    OFFENSIVE_THREAT("Offensive Threat", 86, 0.019, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD, Position.SMALL_FORWARD}),
    SCORING_MACHINE("Scoring Machine", 91, 0.045, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD, Position.SMALL_FORWARD}),

    FLOOR_GENERAL("Floor General", 78, 0.023, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD}),
    POINT_FORWARD("Point Forward", 80, 0.021, new Position[]{Position.SMALL_FORWARD, Position.POWER_FORWARD}),
    PLAYMAKING_SHARPSHOOTER("Playmaking Sharpshooter", 0.034, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD, Position.SMALL_FORWARD}),
    PLAYMAKING_SLASHER("Playmaking Slasher", 0.037, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD, Position.SMALL_FORWARD}),
    PLAYMAKING_DEFENDER("Playmaking Defender", 0.025, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD, Position.SMALL_FORWARD}),
    PLAYMAKING_SHOT_CREATOR("Playmaking Shot Creator", 0.032, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD, Position.SMALL_FORWARD}),

    ELUSIVE_FINISHER("Elusive Finisher", 0.033, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD}),
    SLASHING_SHARPSHOOTER("Slashing Sharpshooter", 0.022, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD, Position.SMALL_FORWARD}),
    SLASHING_PLAYMAKER("Slashing Playmaker", 0.026, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD, Position.SMALL_FORWARD}),
    SLASHING_POINT_FORWARD("Slashing Point Forward", 0.028, new Position[]{Position.SMALL_FORWARD, Position.POWER_FORWARD}),
    SLASHING_DEFENDER("Slashing Defender", 0.033, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD, Position.SMALL_FORWARD}),
    SLASHING_SHOT_CREATOR("Slashing Shot Creator", 0.024, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD, Position.SMALL_FORWARD}),

    THREE_POINT_SPECIALIST("3PT Specialist", 0.033, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD}),
    THREE_AND_D_WING("3 & D Wing", 0.04, new Position[]{Position.SMALL_FORWARD}),
    SHOOTER("Shooter", 0.042, new Position[]{Position.SMALL_FORWARD}),
    SHARPSHOOTING_PLAYMAKER("Sharpshooting Playmaker", 0.027, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD, Position.SMALL_FORWARD}),
    SHARPSHOOTING_SLASHER("Sharpshooting Slasher", 0.023, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD, Position.SMALL_FORWARD}),
    SHARPSHOOTING_SHOT_CREATOR("Sharpshooting Shot Creator", 0.022, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD, Position.SMALL_FORWARD}),

    LOCKDOWN_DEFENDER("Lockdown Defender", 85, 0.02, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD, Position.SMALL_FORWARD}),
    TWO_WAY_SLASHER("Two Way Slasher", 0.028, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD, Position.SMALL_FORWARD}),
    TWO_WAY_PLAYMAKER("Two Way Playmaker", 0.03, new Position[]{Position.POINT_GUARD, Position.SHOOTING_GUARD}),
    TWO_WAY_POINT_FORWARD("Two Way Point Forward", 0.031, new Position[]{Position.SMALL_FORWARD, Position.POWER_FORWARD}),

    ALL_AROUND_POINT_GUARD("All-Around Point Guard", 90, 0.019, new Position[]{Position.POINT_GUARD}),
    ALL_AROUND_SHOOTING_GUARD("All-Around Shooting Guard", 90, 0.019, new Position[]{Position.SHOOTING_GUARD}),
    ALL_AROUND_SMALL_FORWARD("All-Around Small Forward", 90, 0.019, new Position[]{Position.SMALL_FORWARD}),

    RIM_PROTECTOR("Rim Protector", 0.039, new Position[]{Position.POWER_FORWARD, Position.CENTER}),
    STRETCH_BIG("Stretch Big", 0.021, new Position[]{Position.POWER_FORWARD, Position.CENTER}),
    REBOUNDER("Rebounder", 0.037, new Position[]{Position.POWER_FORWARD, Position.CENTER}),
    POST_SCORER("Post Scorer", 0.026, new Position[]{Position.POWER_FORWARD, Position.CENTER}),
    PAINT_BEAST("Paint Beast", 0.033, new Position[]{Position.POWER_FORWARD, Position.CENTER}),
    BIG_GUARD("Big Guard", 83, 0.021, new Position[]{Position.POWER_FORWARD, Position.CENTER}),
    ATHLETIC_FINISHER("Athletic Finisher", 0.024, new Position[]{Position.POWER_FORWARD, Position.CENTER});

    private static final Archetype[] archetypes = values();
    private static final Map<String, Archetype> archetypeMap;
    private static final Map<Position, List<Archetype>> archetypesByPosition;

    static {
        archetypesByPosition = new HashMap<>();
        for (Position position : Position.positions) {
            archetypesByPosition.put(position, new ArrayList<>());
        }
        for (Archetype archetype : archetypes) {
            List<Position> positionsInArchetype = Arrays.asList(archetype.getPositions());
            for (Position position : Position.positions) {
                List<Archetype> archetypesWithPosition = archetypesByPosition.get(position);
                if (positionsInArchetype.contains(position)) {
                    archetypesWithPosition.add(archetype);
                }
            }
        }
    }

    static {
        archetypeMap = new HashMap<>();
        for (Archetype archetype : archetypes) {
            archetypeMap.put(archetype.getLabel(), archetype);
        }
    }

    private final String label;
    private final int minOverall;
    private final double probability;
    private final Position[] positions;

    /**
     * An archetype, dictating the rating of a player, with a minimum overall cap.
     *
     * @param label       the name of the archetype.
     * @param minOverall  the minimum overall required for players with this archetype.
     * @param probability the probability this archetype is randomly selected.
     * @param positions   the positions associated with this archetype.
     */
    Archetype(String label, int minOverall, double probability, Position[] positions) {
        this.label = label;
        this.minOverall = minOverall;
        this.probability = probability;
        this.positions = positions;
    }

    /**
     * An archetype without any minimum overall cap.
     *
     * @param label       the string representation of the archetype.
     * @param probability the probability of the archetype being chosen.
     * @param positions   the positions this archetype is locked to.
     */
    Archetype(String label, double probability, Position[] positions) {
        this.label = label;
        this.minOverall = 0;
        this.probability = probability;
        this.positions = positions;
    }

    /**
     * Generate a random archetype for a given position.
     *
     * @param position the position of the player.
     * @return a random archetype.
     */
    public static Archetype randomArchetype(Position position, int overall) {
        List<Archetype> archetypesWithPosition = getArchetypes(position, overall);
        EnumeratedDistribution<Archetype> distribution = getProbabilityDistribution(archetypesWithPosition);
        return distribution.sample();
    }

    /**
     * Return an array of which archetypes can be selected for the given player.
     *
     * @param position the position of the player.
     * @param overall  the overall of the player, some archetypes are unavailable to player's with lower overalls.
     * @return the archetypes available to select for the given player.
     */
    private static List<Archetype> getArchetypes(Position position, int overall) {
        List<Archetype> archetypesForPosition = archetypesByPosition.get(position);
        List<Archetype> archetypes = new ArrayList<>();
        for (Archetype archetype : archetypesForPosition) {
            if (overall > archetype.getMinOverall())
                archetypes.add(archetype);
        }
        return archetypes;
    }

    /**
     * Calculate the sum of probabilities of choosing each of the available archetypes.
     *
     * @param archetypes the archetypes available to choose from.
     * @return the total probability (to be scaled to 1 later on).
     */
    private static double sumProbabilities(List<Archetype> archetypes) {
        double sum = 0;
        for (Archetype archetype : archetypes) {
            sum += archetype.getProbability();
        }
        return sum;
    }

    /**
     * Generate a scaled probability distribution of the available archetypes.
     *
     * @param archetypes the available archetypes to choose from.
     * @return a probability distribution for each archetype.
     */
    private static EnumeratedDistribution<Archetype> getProbabilityDistribution(List<Archetype> archetypes) {
        double scale = 1 / sumProbabilities(archetypes);
        List<Pair<Archetype, Double>> probabilities = new ArrayList<>();
        for (Archetype archetype : archetypes) {
            double probability = archetype.getProbability() * scale;
            probabilities.add(new Pair<>(archetype, probability));
        }
        return new EnumeratedDistribution<>(probabilities);
    }

    public static Archetype getArchetypeByName(String name) {
        return archetypeMap.getOrDefault(name, null);
    }

    public String getLabel() {
        return label;
    }

    public int getMinOverall() {
        return minOverall;
    }

    public double getProbability() {
        return probability;
    }

    public Position[] getPositions() {
        return positions;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
