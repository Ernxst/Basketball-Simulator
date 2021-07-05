package com.example.app.generators.player;

import com.example.app.util.MathsUtil;
import com.example.app.util.Util;
import com.example.db.interfaces.player.AttributeInterface;
import com.example.entities.player.util.Archetype;
import com.example.entities.player.util.PlayerConstants;
import com.example.entities.player.util.Position;
import com.example.entities.player.util.attributes.Attribute;
import com.example.entities.player.util.attributes.PlayerAttributes;

import java.util.Map;


/**
 * Class to generate various gameplay attribute ratings for a player.
 */
public class AttributesGenerator {
    private final int overall;
    private final int potential;
    private final double potentialAsPercentage;
    private final PlayerAttributes playerAttributes;
    private final PlayerAttributes potentialAttributes;
    private final double ONE_THIRD_OVERALL;
    private final AttributeGeneratorHelper helper;

    /**
     * Initialise a new generator instance for a given player.
     *
     * @param overall   the overall of the player.
     * @param potential the potential overall of the player.
     * @param position  the position of the player.
     * @param archetype the archetype of the player, determining the maximum attribute value caps.
     * @param age       the age of the player, affecting mental and physical attributes.
     * @param weight    the weight of the player, affecting physical attributes.
     * @param height    the height of the player affecting shooting, driving, dribbling,
     *                  defensive, and physical attributes.
     * @param wingspan  the wingspan of the player, affecting shooting, driving, defensive,
     *                  dribbling and physical attributes.
     */
    public AttributesGenerator(int overall, int potential, Position position, Archetype archetype,
                               int age, double weight, double height, double wingspan) {
        this.overall = overall;
        this.ONE_THIRD_OVERALL = overall * (1.0 / 3.0);
        this.potential = potential;
        this.potentialAsPercentage = potential / 100.0;
        helper = new AttributeGeneratorHelper(overall, position, archetype, age, weight, height, wingspan);
        playerAttributes = generatePlayerAttributes();
        potentialAttributes = generatePotentialAttributes();
    }

    public PlayerAttributes getPlayerAttributes() {
        return playerAttributes;
    }

    public PlayerAttributes getPotentialAttributes() {
        return potentialAttributes;
    }

    /**
     * Generate a player's gameplay attributes.
     *
     * @return the player's attributes.
     */
    private PlayerAttributes generatePlayerAttributes() {
        PlayerAttributes attributes = new PlayerAttributes();
        for (Attribute attribute : AttributeInterface.getAttributes()) {
            int attributeValue = generateAttribute(attribute);
            attributes.put(attribute, attributeValue);
        }
        return attributes;
    }

    /**
     * Generate a player's gameplay attributes.
     *
     * @return the player's attributes.
     */
    private PlayerAttributes generatePotentialAttributes() {
        PlayerAttributes attributes = new PlayerAttributes();
        for (Map.Entry<Attribute, Integer> entry : playerAttributes.entrySet()) {
            Attribute attribute = entry.getKey();
            int potentialValue = generatePotentialAttribute(entry.getValue());
            attributes.put(attribute, potentialValue);
        }
        return attributes;
    }

    private int generatePotentialAttribute(int baseValue) {
        if (potential > overall && baseValue < PlayerConstants.MAX_ATTRIBUTE_VALUE) {
            double percentageIncrease = 1 + potentialAsPercentage;
            int potentialAttribute = (int) (baseValue * percentageIncrease);
            if (potentialAttribute > PlayerConstants.MAX_ATTRIBUTE_VALUE) {
                potentialAttribute = Util.randomInt(baseValue, PlayerConstants.MAX_ATTRIBUTE_VALUE);
            }
            return potentialAttribute;
        }
        return baseValue;
    }

    private int generateAttribute(Attribute attribute) {
        int baseValue = generateRawAttribute(attribute);
        switch (attribute.getCategory()) {
            case MENTAL:
                return helper.generateMentalAttribute(attribute, baseValue);
            case PHYSICAL:
                return helper.generatePhysicalAttribute(attribute, baseValue);
            case PLAYMAKING:
                return helper.generatePlaymakingAttribute(attribute, baseValue);
            case OUTSIDE_SCORING:
                return helper.generateShootingAttribute(attribute, baseValue);
            case INSIDE_SCORING:
                return helper.generateInsideScoringAttribute(attribute, baseValue);
            case POST_SCORING:
                return helper.generatePostScoringAttribute(attribute, baseValue);
            case DEFENDING:
                return helper.generateDefensiveAttribute(attribute, baseValue);
            case REBOUNDING:
                return helper.generateReboundingAttribute(attribute, baseValue);
        }
        // Should never reach here
        return Util.randomInt(PlayerConstants.MIN_ATTRIBUTE_VALUE, PlayerConstants.MAX_ATTRIBUTE_VALUE);
    }

    private int generateRawAttribute(Attribute attribute) {
        /*
        Value to be randomly generated should fall in the interval:
            - (a * overall, b * overall) where 0 < a < 1 < b < 2

        According to a normal distribution:
            - mean = overall
            - 3 * stdDev = b * overall - overall
            -            = overall * (b - 1)
            - 3 * stdDev = overall - a * overall
            -            = overall * (1 - a)
        Then it follows that:
            - a = 2 - b
            - b = 2 - a
            - a + b = 2
            - stdDev = (1/3) * overall * (1 - a)
            - stdDev = (1/3) * overall * (b - 1)
         */
        double a = getLowerAttributeCap(attribute);
        double b = getUpperAttributeCap(attribute);
        int minValue = Math.max((int) (a * overall), PlayerConstants.MIN_ATTRIBUTE_VALUE);
        int maxValue = Math.min((int) (b * overall), PlayerConstants.MAX_ATTRIBUTE_VALUE);
        double stdDev = ONE_THIRD_OVERALL * (1 - a);
        return MathsUtil.randomNormalDistributionInRange(minValue, maxValue, overall, stdDev);
    }

    // TODO - Finish getLowerAttributeCap()
    private double getLowerAttributeCap(Attribute attribute) {
        /*
         - Should be affected by physical attributes (where necessary), position and archetype.
         - Returned value should be > 0 and < 1.
         */
        return 0.75;
    }

    // TODO - Finish getUpperAttributeCap()
    private double getUpperAttributeCap(Attribute attribute) {
        /*
         - Should be affected by physical attributes (where necessary), position and archetype.
         - Returned value should be > 1 and < 2.
         */
        return 1.5;
    }
}
