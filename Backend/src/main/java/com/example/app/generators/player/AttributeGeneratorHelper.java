package com.example.app.generators.player;

import com.example.entities.player.util.Archetype;
import com.example.entities.player.util.Position;
import com.example.entities.player.util.attributes.Attribute;

/**
 * Just to reduce number of lines in AttributesGenerator.
 */
public class AttributeGeneratorHelper {
    private final int overall;
    private final Position position;
    private final Archetype archetype;
    private final int age;
    private final double weight;
    private final double height;
    private final double wingspan;

    public AttributeGeneratorHelper(int overall, Position position, Archetype archetype, int age,
                                    double weight, double height, double wingspan) {
        this.overall = overall;
        this.position = position;
        this.archetype = archetype;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.wingspan = wingspan;
    }

    /**
     * Factor in the player's wingspan, height, weight and vertical where necessary.
     *
     * @param attribute the type of attribute.
     * @param rawValue  the raw generated attribute value.
     * @return the attribute value, taking into account various physical attributes.
     */
    private int applyPhysicalAttributes(Attribute attribute, int rawValue) {
        return 0;
    }

    public int generatePostScoringAttribute(Attribute attribute, int rawValue) {
        switch (attribute.getName()) {
            case "POST_HOOK":
                return rawValue * (int) (1 * wingspan * height);
            case "POST_FADEAWAY":
                return rawValue * (int) (1.25 * wingspan * height);
            case "POST_MOVES":
                return rawValue * (int) (1 * weight * height);
        }
        return 0;
    }

    public int generateInsideScoringAttribute(Attribute attribute, int rawValue) {
        switch (attribute.getName()) {
            case "STANDING_LAYUP":
                return rawValue * (int) (1 * wingspan * height * weight);
            case "DRIVING_LAYUP":
                return rawValue * (int) (1.25 * wingspan * height * weight);
            case "STANDING_DUNK":
                return rawValue * (int) (1 * wingspan * height / weight);
            case "DRIVING_DUNK":
                return rawValue * (int) (1.33 * wingspan * height * weight);
            case "CONTACT_DUNK":
                return rawValue * (int) (1.2 * wingspan * height * weight);
            case "SHOT_CLOSE":
                return rawValue * (int) (1 * wingspan);
        }
        return 0;
    }

    public int generateShootingAttribute(Attribute attribute, int rawValue) {
        switch (attribute.getName()) {
            case "FREE_THROW":
                return rawValue * (int) (1 / wingspan);
            // TODO - FORMULAS FOR EVERYTHING BELOW;
            case "MID_RANGE_OPEN_STANDING":
                return rawValue * (int) (1.25 * wingspan * height);
            case "MID_RANGE_OFF_DRIBBLE":
                return rawValue * (int) (1 * weight * height);
            case "MID_RANGE_CONTESTED":
                return rawValue * (int) (1 * wingspan * height);
            case "3PT_OPEN_STANDING":
                return rawValue * (int) (1.25 * wingspan * height);
            case "3PT_OFF_DRIBBLE":
                return rawValue * (int) (1 * weight * height);
            case "3PT_CONTESTED":
                return rawValue * (int) (1 * wingspan * height);
        }
        return 0;
    }

    public int generatePlaymakingAttribute(Attribute attribute, int rawValue) {
        switch (attribute.getName()) {
            case "PASSING_ACCURACY":
                return rawValue * (int) (1 * wingspan * height);
            case "PASSING_VISION":
                return rawValue;
            case "BALL_HANDLING":
                return rawValue * (int) (1 * weight * height);
            case "SPEED_WITH_BALL":
                return rawValue * (int) (1 * wingspan * height);
            case "PASSING_IQ":
                return rawValue * (int) (1.25 * age);
        }
        return 0;
    }

    public int generateReboundingAttribute(Attribute attribute, int rawValue) {
        switch (attribute.getName()) {
            case "OFFENSIVE_REBOUND":
                return rawValue * (int) (1 * wingspan * height);
            case "DEFENSIVE_REBOUND":
                return rawValue * (int) (1.25 * wingspan * height);
            case "BOX_OUT":
                return rawValue * (int) (1 * weight * height);
            case "LATERAL_QUICKNESS":
                return rawValue * (int) (1 * wingspan * height);
            case "DEFENSIVE_AWARENESS":
            case "PLAY_RECOGNITION":
            case "PASS_PERCEPTION":
                return rawValue;
            case "STEAL":
                return rawValue * (int) (1 * weight * height);
            case "BLOCK":
                return rawValue * (int) (1 * weight * height);
            case "SHOT_CONTEST":
                return rawValue * (int) (1 * weight * height);
            case "INTERIOR_DEFENSE":
                return rawValue * (int) (1 * weight * height);
            case "PERIMETER_DEFENSE":
                return rawValue * (int) (1 * weight * height);
        }
        return 0;
    }

    public int generateDefensiveAttribute(Attribute attribute, int rawValue) {
        switch (attribute.getName()) {
            case "STANDING_LAYUP":
                return rawValue * (int) (1 * wingspan * height);
            case "DRIVING_LAYUP":
                return rawValue * (int) (1.25 * wingspan * height);
            case "STANDING_DUNK":
                return rawValue * (int) (1 * weight * height);
            case "DRIVING_DUNK":
                return rawValue * (int) (1 * wingspan * height);
            case "CONTACT_DUNK":
                return rawValue * (int) (1.25 * wingspan * height);
            case "SHOT_CLOSE":
                return rawValue * (int) (1 * weight * height);
        }
        return 0;
    }

    public int generatePhysicalAttribute(Attribute attribute, int rawValue) {
        switch (attribute.getName()) {
            case "SPEED":
                return rawValue * (int) (1 * wingspan * height);
            case "STAMINA":
                return rawValue * (int) (1.25 * wingspan * height);
            case "STRENGTH":
                return rawValue * (int) (1 * weight * height);
            case "VERTICAL":
                return rawValue * (int) (1 * wingspan * height);
            case "DURABILITY":
                return rawValue * (int) (1.25 * wingspan * height);
            case "AGILITY":
                return rawValue * (int) (1 * weight * height);
            case "ACCELERATION":
                return rawValue * (int) (1 * weight * height);
        }
        return 0;
    }

    public int generateMentalAttribute(Attribute attribute, int rawValue) {
        switch (attribute.getName()) {
            case "SHOT_IQ":
                return rawValue * (int) (1.25 * age);
            case "BASKETBALL_IQ":
                return rawValue * (int) (1.4 * age);
            case "OFFENSIVE_CONSISTENCY":
                return rawValue * (int) (1.33 * age);
            case "DEFENSIVE_CONSISTENCY":
            case "CONFIDENCE":
            case "HANDS":
                return rawValue;
            case "REACTION_TIME":
                return rawValue * (int) (1.0 / age);
            case "DRAW_FOUL":
                return rawValue * (int) (1.1 * age);
        }
        return 0;
    }
}
